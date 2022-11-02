package egovframework.let.popup.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.popup.service.PopupService;
import egovframework.let.popup.service.PopupVO;
import egovframework.let.utl.fcc.service.EgovDateUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("popupService")
public class PopupServiceImpl extends EgovAbstractServiceImpl implements PopupService {

	@Resource(name= "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Resource(name= "popupMapper")
	private PopupMapper popupMapper;
	
	@Resource(name= "popupIdGnrService")
	private EgovIdGnrService idgenService;
	
	//캐시 : 처음에 DB를 접속 후 캐시로. 매번 DB접속X
	//팝업 리스트Hash
	private HashMap<String, List<EgovMap>> popupHash = new HashMap<String, List<EgovMap>>();
	
	//변수관련 Hash
	private HashMap<String, String> cacheMap = new HashMap<String, String>();
	
	
	//팝업 목록 가져오기
	@Override
	public List<EgovMap> selectPopupList(PopupVO vo) throws Exception {
		return popupMapper.selectPopupList(vo);
	}

	//팝업 목록 수
	@Override
	public int selectPopupListCnt(PopupVO vo) throws Exception {
		return popupMapper.selectPopupListCnt(vo);
	}

	//팝업 등록하기
	@Override
	public String insertPopup(PopupVO vo) throws Exception {
		String id = idgenService.getNextStringId();
		vo.setPopupId(id);
		popupMapper.insertPopup(vo);
		
		//팝업목록 초기화 (캐시메모리에 담긴걸 사용하기 위해 등록 시 초기화해줌)
		this.popupHash.remove("popupList");
		
		return id;
	}
	
	//팝업상세
	public EgovMap selectPopup(PopupVO vo) throws Exception {
		return popupMapper.selectPopup(vo);
	}
	
	//팝업 수정하기
	public void updatePopup(PopupVO vo) throws Exception {				
		popupMapper.updatePopup(vo);
		
		//팝업목록 초기화
		this.popupHash.remove("popupList");
	}
		
	//팝업 삭제하기
	public void deletePopup(PopupVO vo) throws Exception {
		popupMapper.deletePopup(vo);
		
		//팝업목록 초기화
		this.popupHash.remove("popupList");
	}
	
	
	//서비스 팝업 목록 가져오기
	public List<EgovMap> selectPopupServiceList(PopupVO vo) throws Exception {
		List<EgovMap> popupList = new ArrayList<EgovMap>();
		String cacheDay = this.cacheMap.get("today"); //서버를 처음 키면 cacheDay = null //다시 실행시 아까 들어간 today(오늘 날짜)
		String today = EgovDateUtil.getToday("yyyyMMdd");
		
		//캐시 메모리에 팝업목록이 있는지 체크
		if(!this.popupHash.containsKey("popupList") || !today.equals(cacheDay)) { //처움 킨 상태는 둘 다 null //값이 있으니 else로
			List<EgovMap> resultList = popupMapper.selectPopupList(vo);
			if(resultList != null && resultList.size() > 0) { 
				//날짜 저장
				this.cacheMap.remove("today"); //초기화
				this.cacheMap.put("today", EgovDateUtil.getToday("yyyyMMdd")); //
				
				for(int i=0; i<resultList.size(); i++) {
					long sl = Long.parseLong(resultList.get(i).get("ntceBgnde").toString().replaceAll("-", ""));
					long el = Long.parseLong(resultList.get(i).get("ntceEndde").toString().replaceAll("-", ""));
					long cl = Long.parseLong(EgovDateUtil.getToday("yyyyMMdd"));
					if(cl <= el && cl >= sl) { //오늘날짜 기준으로 시작일과 종료일을 계산해서 맞는걸 가져옴
						popupList.add(resultList.get(i));
					}
				}
			}
			this.popupHash.remove("popupList"); //기존에 있을수도 있으니 remove
			this.popupHash.put("popupList", popupList); //for문을 돌려서 나온 것만 put
		}else { //캐시메모리가 있으니 else로 옴. 캐시에 담겨있는거 List에 넣자 
			popupList = this.popupHash.get("popupList");
		}
		
		return popupList;
	}
}
