package egovframework.let.crud.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import egovframework.com.cmm.ComDefaultVO;

public class CrudVO extends ComDefaultVO implements Serializable {
	//데이터
	private String crudId; //Id
	private String crudSj; //제목
	private String crudCn; //내용
	private String userNm; //작성자
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date frstRegistPnttm; //Date 타입을 맞춰준다
	
	//GetSet (Alt Shift S)
	public String getCrudId() {
		return crudId;
	}
	public void setCrudId(String crudId) {
		this.crudId = crudId;
	}

	public String getCrudSj() {
		return crudSj;
	}
	public void setCrudSj(String crudSj) {
		this.crudSj = crudSj;
	}
	
	public String getCrudCn() {
		return crudCn;
	}
	public void setCrudCn(String crudCn) {
		this.crudCn = crudCn;
	}
	
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	
	public Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}
	public void setFrstRegistPnttm(Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}
	
}
