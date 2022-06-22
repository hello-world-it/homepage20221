package egovframework.let.board.web;
import java.util.List; ////선언을 했는데 쓰지 않으면 노란줄이 생김
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.fcc.service.FileMngUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class BoardController {
	
	////상속을 받아 사용
	@Resource(name= "boardService") ////name은 ServiceImpl의 @Service("crudService") 값과 동일해야 호출이 가능
	private BoardService boardService;
  //private   클래스객체명           변수명;  	//클래스객체명은 앞글자 대문자, (내가앞으로사용할)변수명 앞글자 소문자
	
	//220615 파일추가
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name="fileMngUtil")
	private FileMngUtil fileUtil;
	//
	
	//1.게시물 목록 가져오기
	@RequestMapping(value = "/board/selectList.do")
	public String selectList(@ModelAttribute("searchVO") BoardVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		/* 목록만 가져오기
		List<EgovMap> resultList = tempService.selectTempList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		*/
		
//		전자정부의 PaginationInfo를 가져다 쓴다 (라이브러리 활용) / 220420 Ctrl클릭해서 확인가능
//		currentPageNo : 현재 페이지 번호 / recordCountPerPage : 한 페이지당 게시되는 게시물 건 수
//		pageSize : 페이지 리스트에 게시되는 페이지 건수 / totalRecordCount : 전체 게시물 건 수 
		
		// 공지 게시글 220518
		searchVO.setNoticeAt("Y"); //쿼리로 가서 공지글을 가지고 옴
		List<EgovMap> noticeResultList = boardService.selectBoardList(searchVO);
		model.addAttribute("noticeResultList", noticeResultList);
		//
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		//파라미터를 받는다
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		//220518
		searchVO.setNoticeAt("N"); //공지글이 아니면 일반글을 가지고 옴. 이걸 안쓰면 위에 선언한 setNoticeAt("Y") 때문에 공지글만 나오게 된다
		List<EgovMap> resultList = boardService.selectBoardList(searchVO);
		model.addAttribute("resultList", resultList);
		//필요 시에만 connect / 가비지컬렉션
		
		int totCnt = boardService.selectBoardListCnt(searchVO);
		//총 갯수
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		/* 목록 가져오기
		List<EgovMap> resultList = boardService.selectBoardList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		*/
		
		//220518 사용자 정보 
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		//
			
		return "board/BoardSelectList"; //jsp페이지
	}
		

	//2.게시물 등록/수정  (1)화면으로 가고)
	@RequestMapping(value = "/board/boardRegist.do") //주소로 갔을 때(SelectList에서) 해당 소스가 실행 
	public String boardRegist(@ModelAttribute("searchVO") BoardVO boardVO, //url을 사용자가 쓸 수 있게 만들어준다
			HttpServletRequest request, ModelMap model) throws Exception {
		
		//로그인 시 글 등록 가능
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser(); //LoginVO 세션에 저장된 값 가져오기 (로그인 정보 가져오기) 
		if(user == null || user.getId() == null) { 
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/board/selectList.do";
		}else {
			model.addAttribute("USER_INFO", user); //ID체크 (내가 접속 한 사람과 글 쓴 사람과 대조 시 사용)
		}
		
		//id값 확인해서 등록을 할지 수정을 할지 체크
		BoardVO result = new BoardVO();
		if(!EgovStringUtil.isEmpty(boardVO.getBoardId())) {
			result=boardService.selectBoard(boardVO);
			//본인 및 관리자만 허용 (ex비밀글) / 서버에서 해킹의 위험 방지
			if(!user.getId().equals(result.getFrstRegisterId()) && !"admin".equals(user.getId())) {
				model.addAttribute("message", "작성자 본인만 확인 가능합니다.");
				return "forward:/board/selectList.do";
			}
		} //수정
		model.addAttribute("result", result); //둥록
		
		request.getSession().removeAttribute("sessionBoard"); //등록을 하면서 session을 만들고, 새로 등록 시 session을 지워 주고 새 글을 등록
		
		return "board/BoardRegist";
	} 
	
	
	//3.게시물 등록 (2)디비에 등록)
	@RequestMapping(value="/board/insert.do")
	public String insert(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO searchVO,
		HttpServletRequest request, ModelMap model) throws Exception {
		
		
		//이중 서브밋 방지 체크
		if(request.getSession().getAttribute("sessionBoard") != null) { //2)등록 후 F5 시 not null(session에 값이 있음)이므로 selectList.do로 이동 (이중등록방지)
			return "forward:/board/selectList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser(); //3)로그인 정보를 가지고 와서 없을 시 글 쓰기 불가 
		if(user == null || user.getId() == null) { //로그인 했다가 일정 시간이 지난 후 로그아웃이 되면 로그인 정보가 없어지므로 message를 띄우고 
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/board/selectList.do"; //로그인페이지로 이동 후 로그인이 되면 현재 글쓰던 페이지의 url을 세션에 저장했다가 글쓰던 페이지로 이동시켜줌
		}
		
		//220615
		List<FileVO> result = null;
		String atchFileId = "";
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if(!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "BOARD_", 0, "", "board.fileStorePath");
			atchFileId = fileMngService.insertFileInfs(result);
		}
		searchVO.setAtchFileId(atchFileId);
		//
		
		searchVO.setCreatIp(request.getRemoteAddr()); //4)작성자의 공인IP를 받아옴 getRemoteAddr()
		searchVO.setUserId(user.getId()); //userId를 만들어서 아이디를 받아옴
		
		boardService.insertBoard(searchVO); //insert실행
		
		//이중 서브밋 방지. 
		request.getSession().setAttribute("sessionBoard", searchVO); //1)등록이 일어날 때 session에 저장해 
		return "forward:/board/selectList.do";
	}
	
	
	//4.게시물 가져오기
	@RequestMapping(value = "/board/select.do")
	public String select(@ModelAttribute("searchVO") BoardVO searchVO, 
			HttpServletRequest request, ModelMap model) throws Exception{
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user); //
		
		BoardVO result = boardService.selectBoard(searchVO); //게시물 상세정보 가져오기 (Impl)
		// 비밀 글여부 체크
		if("Y".equals(result.getOthbcAt())) {
			// 본인 및 관리자만 허용★ / 각 게시판 속성을 받아 게시판을 관리(분리)
			if(user == null || user.getId() == null || (!user.getId().equals(result.getFrstRegisterId()) && !"admin".equals(user.getId()))) {
				model.addAttribute("message", "작성자 본인만 확인 가능합니다."); 
				return "forward:/board/selectList.do";
			}
		}
		model.addAttribute("result", result);
		return "board/BoardSelect";
	}
	
	//5.게시물 수정하기
	@RequestMapping(value = "/board/update.do")
	public String update(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		// 이중 서브밋 방지
		if(request.getSession().getAttribute("sessionBoard") != null) {
			return "forward:/board/selectList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/board/selectList.do";
		}else if("admin".equals(user.getId())) {
			searchVO.setMngAt("Y");
		}
		
		searchVO.setUserId(user.getId());
		
		boardService.updateBoard(searchVO); //★해커침입방지 1.스크립트,jsp 2.java 3.sql
		
		
		//220622 / 수정도 첨부파일 있을 때만 실행
		String atchFileId = searchVO.getAtchFileId();
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if(!files.isEmpty()) { //파일이 있으면 
			if(EgovStringUtil.isEmpty(atchFileId)) { //첨부파일 아이디가 없으면		sn(펌부파일수)=0 ""  저장경로(global에서 셋팅) -> 등록과 같다
				List<FileVO> result = fileUtil.parseFileInf(files, "BOARD_", 0, "", "board.fileStorePath");
				atchFileId = fileMngService.insertFileInfs(result);
				searchVO.setAtchFileId(atchFileId);
			}else { //수정 시 파일이 있으면  
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId); //파일아이디를 가지고
				int cnt = fileMngService.getMaxFileSN(fvo); //SN을 구해온다 (추가 파일을 위한 갯수를 세어 그 다음 번호로 준다)
				List<FileVO> _result = fileUtil.parseFileInf(files, "BOARD_", cnt, atchFileId, "board.fileStorePath");
				fileMngService.updateFileInfs(_result);
			}
		}
		searchVO.setUserId(user.getId());
		
		boardService.updateBoard(searchVO);
		
		
		// 이중 서브밋 방지
		request.getSession().setAttribute("sessionBoard", searchVO);
		return "forward:/board/selectList.do";
	}
	
	//6.게시물 삭제하기
	@RequestMapping(value = "/board/delete.do")
	public String delete(@ModelAttribute("searchVO") BoardVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/board/selectList.do";
		}else if("admin".equals(user.getId())) {
			searchVO.setMngAt("Y");
		}
		
		searchVO.setUserId(user.getId());
		
		boardService.deleteBoard(searchVO);
		
		return "forward:/board/selectList.do";
	}
	
	//JSTL / 220420  폴더 위치 생각하기...
/*	//어노테이션에 url?? 
	@RequestMapping(value="/temp/jstl.do")
	public String jstl(@ModelAttribute("searchVO") TempVO searchVO, 
			HttpServletRequest request, ModelMap model) throws Exception {
		return "/temp/Jstl";
	} //temp폴더에 Jstl.jsp
	
	//JSTL Import용
	@RequestMapping(value="/temp/jstlImport.do")
	public String jstlImport(@ModelAttribute("searchVO") TempVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		return "/temp/JstlImport";
	}*/	
}