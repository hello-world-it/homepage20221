package egovframework.let.crud3.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface Crud3Service {
	
	//impl에서 가져와서 만들 수 있다 
	//데이터(서비스) 가져오기(연결) 
	////통로역할 
	
	//데이터 목록 가져오기
	public List<EgovMap> selectCrudList(Crud3VO vo) throws Exception;
	
	//데이터 목록 수
	public int selectCrudListCnt(Crud3VO vo) throws Exception;
	
	//데이터 등록하기
	public String insertCrud(Crud3VO vo) throws Exception;
		
	//데이터 가져오기 
	public Crud3VO selectCrud(Crud3VO vo) throws Exception;
	
	//데이터 수정하기
	public void updateCrud(Crud3VO vo) throws Exception;
	
	//데이터 삭제하기
	public void deleteCrud(Crud3VO vo) throws Exception;
	
	
}