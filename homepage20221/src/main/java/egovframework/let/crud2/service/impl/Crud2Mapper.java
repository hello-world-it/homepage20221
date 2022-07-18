package egovframework.let.crud2.service.impl;

import java.util.List;

import egovframework.let.crud2.service.Crud2VO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Mapper("crud2Mapper") //mapper 선언
public interface Crud2Mapper {
	
	//데이터 목록 가져오기
	List<EgovMap> selectCrudList(Crud2VO vo) throws Exception;
	
	//데이터 목록 수 / 220420
	int selectCrudListCnt(Crud2VO vo) throws Exception;
	
	//데이터 등록 / 등록,수정,삭제 시 db에서 받아오는 결과 값이 없어서 void 선언 - 무슨 형(String, int, EgovMap 등)으로 받는지 체크!
	void insertCrud(Crud2VO vo) throws Exception;
	
	//데이터 가져오기 / selectCrud는 Crud_SQL의 <mapper namespace="egovframework.let.temp.service.impl.CrudMapper">에 연결됨
	Crud2VO selectCrud(Crud2VO vo) throws Exception;
//	객체선언  
	
	//데이터 수정하기
	void updateCrud(Crud2VO vo) throws Exception;
	
	//데이터 삭제하기
	void deleteCrud(Crud2VO vo) throws Exception;
	
	
}
