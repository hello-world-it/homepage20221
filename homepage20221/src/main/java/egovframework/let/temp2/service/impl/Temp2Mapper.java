package egovframework.let.temp2.service.impl;
import java.util.Iterator;
import java.util.List;

import egovframework.let.cop.bbs.service.Board;
import egovframework.let.cop.bbs.service.BoardVO;
import egovframework.let.temp2.service.Temp2VO;
//import egovframework.let.temp.service.TempVO;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import org.springframework.stereotype.Repository;

@Mapper("temp2Mapper") //mapper 선언
public interface Temp2Mapper {
	
	//임시데이터 가져오기 / selectTemp는 Temp_SQL의 <mapper namespace="egovframework.let.temp.service.impl.TempMapper">에 연결됨
	Temp2VO selectTemp(Temp2VO vo) throws Exception;
	
	//임시데이터 목록 가져오기
	List<EgovMap> selectTempList(Temp2VO vo) throws Exception;
	
	//임시데이터 등록 / 등록,수정,삭제 시 db에서 받아오는 결과 값이 없어서 void 선언 - 무슨 형(String, int, EgovMap 등)으로 받는지 체크!
	void insertTemp(Temp2VO vo) throws Exception;
	
	//임시데이터 수정하기
	void updateTemp(Temp2VO vo) throws Exception;
	
	//임시데이터 삭제하기
	void deleteTemp(Temp2VO vo) throws Exception;
	
	//임시데이터 목록 수 / 220420
	int selectTempListCnt(Temp2VO vo) throws Exception;
}
