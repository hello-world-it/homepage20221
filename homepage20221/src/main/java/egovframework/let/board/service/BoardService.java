package egovframework.let.board.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface BoardService {
	
	//impl에서 가져와서 만들 수 있다 
	//데이터(서비스) 가져오기(연결) 
	////통로역할 
	
	//게시물 목록 가져오기
	public List<EgovMap> selectBoardList(BoardVO vo) throws Exception;
	
	//게시물 목록 수
	public int selectBoardListCnt(BoardVO vo) throws Exception;
	
	//게시물 등록하기
	public String insertBoard(BoardVO vo) throws Exception;
	
	//4.게시물 가져오기
	public BoardVO selectBoard(BoardVO vo) throws Exception;
	
	//5.게시물 수정하기
	public void updateBoard(BoardVO vo) throws Exception;
	
	//6.게시물 삭제하기
	public void deleteBoard(BoardVO vo) throws Exception;
	
}