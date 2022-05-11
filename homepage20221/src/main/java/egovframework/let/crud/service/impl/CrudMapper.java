package egovframework.let.crud.service.impl;

import java.util.List;

import egovframework.let.crud.service.CrudVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Mapper("crudMapper") //mapper 선언
public interface CrudMapper {
	
	//데이터 목록 가져오기
	List<EgovMap> selectCrudList(CrudVO vo) throws Exception;
	
	//데이터 목록 수 / 220420
	int selectCrudListCnt(CrudVO vo) throws Exception;
	
	//데이터 등록 / 등록,수정,삭제 시 db에서 받아오는 결과 값이 없어서 void 선언 - 무슨 형(String, int, EgovMap 등)으로 받는지 체크!
	void insertCrud(CrudVO vo) throws Exception;
	
	//데이터 가져오기 / selectTemp는 Temp_SQL의 <mapper namespace="egovframework.let.temp.service.impl.TempMapper">에 연결됨
	CrudVO selectCrud(CrudVO vo) throws Exception;
//	객체선언  
	
	//데이터 수정하기
	void updateCrud(CrudVO vo) throws Exception;
	
	//데이터 삭제하기
	void deleteCrud(CrudVO vo) throws Exception;
	
	
}
