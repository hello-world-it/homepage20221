package egovframework.let.board.service.impl;

import java.util.List;

import egovframework.let.board.service.BoardVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Mapper("boardMapper") //mapper 선언
public interface BoardMapper {
	
	//게시물 목록 가져오기
	List<EgovMap> selectBoardList(BoardVO vo) throws Exception;
	
	//게시물 목록 수 
	int selectBoardListCnt(BoardVO vo) throws Exception;
	
	//게시물 등록 / 등록,수정,삭제 시 db에서 받아오는 결과 값이 없어서 void 선언 - 무슨 형(String, int, EgovMap 등)으로 받는지 체크!
	void insertBoard(BoardVO vo) throws Exception;
	
	//조회수 업데이트
	void updateViewCnt(BoardVO vo) throws Exception;
	
	//게시물 상세정보 / selectBoard는 Board_SQL의 <mapper namespace="egovframework.let.temp.service.impl.BoardMapper">에 연결됨
	BoardVO selectBoard(BoardVO vo) throws Exception;
//	객체선언  
	
	//게시물 수정하기
	void updateBoard(BoardVO vo) throws Exception;
	
	//게시물 삭제하기
	void deleteBoard(BoardVO vo) throws Exception;
	
	
}
