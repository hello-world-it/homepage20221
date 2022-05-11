package egovframework.let.crud.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CrudService {
	
	//impl에서 가져와서 만들 수 있다 
	//데이터(서비스) 가져오기(연결) 
	////통로역할 
	
	
	//데이터 목록 가져오기
	public List<EgovMap> selectCrudList(CrudVO vo) throws Exception;
	
	//데이터 목록 수 / 220420
	public int selectCrudListCnt(CrudVO vo) throws Exception;
	
	//데이터 등록하기
	public String insertCrud(CrudVO vo) throws Exception;
		
	//데이터 가져오기 
	public CrudVO selectCrud(CrudVO vo) throws Exception;
	
	//데이터 수정하기
	public void updateCrud(CrudVO vo) throws Exception;
	
	//데이터 삭제하기
	public void deleteCrud(CrudVO vo) throws Exception;
	
	
}