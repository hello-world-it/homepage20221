package egovframework.let.popup.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.cmm.ComDefaultVO;

public class PopupVO extends ComDefaultVO implements Serializable {

	//팝업ID
	private String popupId;
	//시스템수분코드(TYPE1:일반팝업, TYPE2:레이어팝업)
	private String sysTyCode;
	//팝업명
	private String popupTitleNm;
	//팝업창위치_세로
	private String popupHlc;
	//팝업창위치_가로
	private String popupWlc;
	//팝업창사이즈_세로
	private String popupHsize;
	//팝업창사이즈_가로
	private String popupWsize;
	//팝업내용
	private String popupCn;
	//게시시작일
	private String ntceBgnde;
	//게시종료일
	private String ntceEndde;	
	//사용여부
	private String useAt; 
	//최초등록시점
	private Date frstRegistPnttm;
	//최초등록자ID
	private String frstRegisterId;
	//최종수정시점
	private String lastUpdtPnttm;
	//최종수정자ID
	private String lastUpdusrId;
	//사용자ID
	private String userId;
	
	
	public String getPopupId() {
		return popupId;
	}
	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}
	public String getSysTyCode() {
		return sysTyCode;
	}
	public void setSysTyCode(String sysTyCode) {
		this.sysTyCode = sysTyCode;
	}
	public String getPopupTitleNm() {
		return popupTitleNm;
	}
	public void setPopupTitleNm(String popupTitleNm) {
		this.popupTitleNm = popupTitleNm;
	}
	public String getPopupHlc() {
		return popupHlc;
	}
	public void setPopupHlc(String popupHlc) {
		this.popupHlc = popupHlc;
	}
	public String getPopupWlc() {
		return popupWlc;
	}
	public void setPopupWlc(String popupWlc) {
		this.popupWlc = popupWlc;
	}
	public String getPopupHsize() {
		return popupHsize;
	}
	public void setPopupHsize(String popupHsize) {
		this.popupHsize = popupHsize;
	}
	public String getPopupWsize() {
		return popupWsize;
	}
	public void setPopupWsize(String popupWsize) {
		this.popupWsize = popupWsize;
	}
	public String getPopupCn() {
		return popupCn;
	}
	public void setPopupCn(String popupCn) {
		this.popupCn = popupCn;
	}
	public String getNtceBgnde() {
		return ntceBgnde;
	}
	public void setNtceBgnde(String ntceBgnde) {
		this.ntceBgnde = ntceBgnde;
	}
	public String getNtceEndde() {
		return ntceEndde;
	}
	public void setNtceEndde(String ntceEndde) {
		this.ntceEndde = ntceEndde;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	public Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
