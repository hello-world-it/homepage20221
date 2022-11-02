package egovframework.let.popup.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface PopupService {
	
	//팝업 목록 가져오기
	public List<EgovMap> selectPopupList(PopupVO vo) throws Exception;
	
	//팝업 목록 수
	public int selectPopupListCnt(PopupVO vo) throws Exception;
	
	//팝업 등록하기
	public String insertPopup(PopupVO vo) throws Exception;

	//팝업 상세
	public EgovMap selectPopup(PopupVO vo) throws Exception;
	
	//팝업 수정하기
	public void updatePopup(PopupVO vo) throws Exception;
		
	//팝업 삭제하기
	public void deletePopup(PopupVO vo) throws Exception;
	
	//서비스 팝업 목록 가져오기
	public List<EgovMap> selectPopupServiceList(PopupVO vo) throws Exception;
}
