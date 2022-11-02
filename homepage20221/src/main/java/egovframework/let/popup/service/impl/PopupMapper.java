package egovframework.let.popup.service.impl;

import java.util.List;

import egovframework.let.popup.service.PopupVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Mapper("popupMapper")
public interface PopupMapper {

	//팝업 목록 가져오기
	List<EgovMap> selectPopupList(PopupVO vo) throws Exception;
		
	//팝업 목록 수
	int selectPopupListCnt(PopupVO vo) throws Exception;
	
	//팝업 등록하기
	void insertPopup(PopupVO vo) throws Exception;
	
	//팝업상세
	EgovMap selectPopup(PopupVO vo) throws Exception;
	
	//팝업 수정하기
	void updatePopup(PopupVO vo) throws Exception;
	
	//팝업 삭제하기
	void deletePopup(PopupVO vo) throws Exception;
}