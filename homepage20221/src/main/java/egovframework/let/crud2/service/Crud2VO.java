package egovframework.let.crud2.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import egovframework.com.cmm.ComDefaultVO;

public class Crud2VO extends ComDefaultVO implements Serializable {
	//데이터
	private String crud2Id; //Id
	private String crud2Sj; //제목
	private String crud2Cn; //내용
	private String user2Nm; //작성자
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date frst2RegistPnttm; //Date 타입을 맞춰준다
	
	//GetSet (Alt Shift S)
	public String getCrud2Id() {
		return crud2Id;
	}
	public void setCrud2Id(String crud2Id) {
		this.crud2Id = crud2Id;
	}
	
	public String getCrud2Sj() {
		return crud2Sj;
	}
	public void setCrud2Sj(String crud2Sj) {
		this.crud2Sj = crud2Sj;
	}
	
	public String getCrud2Cn() {
		return crud2Cn;
	}
	public void setCrud2Cn(String crud2Cn) {
		this.crud2Cn = crud2Cn;
	}
	
	public String getUser2Nm() {
		return user2Nm;
	}
	public void setUser2Nm(String user2Nm) {
		this.user2Nm = user2Nm;
	}
	
	public Date getFrst2RegistPnttm() {
		return frst2RegistPnttm;
	}
	public void setFrst2RegistPnttm(Date frst2RegistPnttm) {
		this.frst2RegistPnttm = frst2RegistPnttm;
	}
	
}
