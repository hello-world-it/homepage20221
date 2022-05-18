package egovframework.let.board.service.impl;

import java.util.List;

import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("boardService") ////고유값. 어노테이션에 명칭을 사용 할 때 소문자로 시작. 현재 프로젝트 기준으로 하나만 있어야 한다(중복X)
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService {

	@Resource(name = "boardMapper")
	private BoardMapper boardMapper;
	
	//context-idgen의 추가한 IdGen과 연결  
	@Resource(name = "egovBoardIdGnrService")
	private EgovIdGnrService idgenService;
	
	//게시물 목록 가져오기
//	@Override 생략가능
	public List<EgovMap> selectBoardList(BoardVO vo) throws Exception { 
		return boardMapper.selectBoardList(vo); 
	}
	
	//게시물 목록 수
//	@Override
	public int selectBoardListCnt(BoardVO vo) throws Exception {
		return boardMapper.selectBoardListCnt(vo);
	}
		
	
	
	
	//임시데이터 등록하기 / String으로 받아오는 id값 체크
	/* public String insertCrud(CrudVO vo) throws Exception {
			crudMapper.insertCrud(vo); //void형식으로 받아오는데 String으로 받기 위해 따로 선언 후 아래에 return null
			return null; } */
	
}
