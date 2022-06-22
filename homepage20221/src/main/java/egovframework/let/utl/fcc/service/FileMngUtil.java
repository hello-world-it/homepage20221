package egovframework.let.utl.fcc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;

//빈 클래스에서 빈을 직접 등록  = <bean class="~" />
@Component("fileMngUtil") 
public class FileMngUtil {

	public static final int BUFF_SIZE = 2048;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Resource(name = "egovFileIdGnrService")
	private EgovIdGnrService idgenService;
	
	//첨부파일에 대한 목록 정보를 취득한다 (정보를 받아오는 메소드)
	public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, 
			int fileKeyParam, String atchFileId, String storePath) throws Exception {
			//변수로 받을 것들: files(파일), KeyStr(앞에붙일숫자), fileKeyParam(수정시 필요,기존 파일에 덮어쓰기X 번호를 매김), atchFileId(수정할때 id가 있으면 받아오고,없으면 등록), storePath(저장경로)
			
		int fileKey = fileKeyParam; //등록 시 0
		
		//파일저장경로
		String storePathString = "";
		//첨부파일ID
		String atchFileIdString = "";
		
		//파일 저장경로 여부
		if ("".equals(storePath) || storePath == null) {
			storePathString = propertyService.getString("Globals.fileStorePath"); //디폴트경로 
		} else {
			storePathString = propertyService.getString(storePath); //context-properties.xml에 명시한 저장 경로에 저장
		}
		
		//첨부파일ID 생성 및 업데이트 여부
		if ("".equals(atchFileId) || atchFileId == null) {
			atchFileIdString = idgenService.getNextStringId(); //id가 없으면 1씩 증가하며 idgen을 받아옴
		} else {
			atchFileIdString = atchFileId; //id가 있으면 순번을 받아와서 다음번호로 넣음
		}
		
		//폴더경로 설정 / File java.io import
		File saveFolder = new File(storePathString); //지정한 경로로 파일을 만들어
		if (!saveFolder.exists() || saveFolder.isFile()) { //폴더가 존재하지 않으면
			saveFolder.mkdirs(); //시스템 상에서 폴더를 만들어
		}
		
		//파일변수  / Iterator, Entry -> java.util 
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator(); //파일 여러개를  배열에 담아 Itreator
		
		MultipartFile file;
		String filePath = "";
		List<FileVO> result = new ArrayList<FileVO>();
		FileVO fvo;
		
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();
			
			file = entry.getValue();
			String orginFileName = file.getOriginalFilename(); //원본파일명이 있으면 파일 이름을 받아
			
			//-------------------------------------------------------
			// 원 파일명이 없는 경우 처리 (첨부가 되지 않는 input file type)
			//// 파일 여러개 첨부시, 하나 올리고 하나 안올리고 하나 올리는 식으로 올리는 경우 필요
			//-------------------------------------------------------
			if ("".equals(orginFileName)) {
				continue;
			}
			////------------------------------------ continue로 다시 while문 반복;
			
			//파일확장자 체크 / 게시판에 일정 파일(예 ppt나 한글만 올려달라) 올릴 때 사용
			int index = orginFileName.lastIndexOf("."); //마지막 글자의 .을 찾아 확장자 .jpg 가져오기 
			String fileExt = orginFileName.substring(index + 1);
			
			//저장파일명 / 원본파일을 받아서 BOARD_~ 명칭을 바꿔서 저장 (첨부파일의 이름이 같은 경우가 있기 때문에)
			String newName = KeyStr + EgovStringUtil.getTimeStamp() + fileKey;
			
			//파일사이즈
			long size = file.getSize();
			
			//파일저장 
			if (!"".equals(orginFileName)) {
				filePath = storePathString + File.separator + newName; //파일 = 저장경로 + 구분자(\:폴더경로랑 파일을 구분) + 이름
				file.transferTo(new File(filePath)); //로컬로 저장
			}
			fvo = new FileVO(); //디비에 저장하기 위한 셋팅
			fvo.setFileExtsn(fileExt); 
			fvo.setFileStreCours(storePathString);
			fvo.setFileMg(Long.toString(size));
			fvo.setOrignlFileNm(orginFileName);
			fvo.setStreFileNm(newName);
			fvo.setAtchFileId(atchFileIdString);
			fvo.setFileSn(String.valueOf(fileKey));
			
			result.add(fvo);
			
			fileKey++;
		}
		
		return result;
		
	}
}
