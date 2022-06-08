package egovframework.let.board.service.impl;

import java.util.List;

import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

//Impl은 단순 통로 뿐만 아니라, 트랜잭션을 처리

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
		
	//게시물 등록
	@Override
	public String insertBoard(BoardVO vo) throws Exception {
		String id = idgenService.getNextStringId();
		vo.setBoardId(id);
		boardMapper.insertBoard(vo);
		
		return id; 
	}
	
	//게시물 상세정보 (4.게시물 가져오기)
	@Override
	public BoardVO selectBoard(BoardVO vo) throws Exception { //트리거 (selectBoard가 실행 되면 안의 두 문장 모두 실행. 에러 시 모두 롤백)
		// 조회수 업 
		// Impl에 넣은건 트랜잭션 처리를 위한 것. Controller에 적었을 경우 둘 중 작동 중간에 에러가 나면, 하나만 실행되고 에러 시 종료 되는 상황을 방지하기 위해 Impl에 작성. 예)쇼핑몰 주문, 통계
		boardMapper.updateViewCnt(vo);
		
		return boardMapper.selectBoard(vo); //return 후 자동 close 
	}
	
	//5.게시물 수정하기
	@Override
	public void updateBoard(BoardVO vo) throws Exception {
		boardMapper.updateBoard(vo);
	}
	
	//6.게시물 삭제하기
	@Override
	public void deleteBoard(BoardVO vo) throws Exception {
		boardMapper.deleteBoard(vo);
	}
	
	
	//임시데이터 등록하기 / String으로 받아오는 id값 체크
	/* public String insertCrud(CrudVO vo) throws Exception {
			crudMapper.insertCrud(vo); //void형식으로 받아오는데 String으로 받기 위해 따로 선언 후 아래에 return null
			return null; } */
	
}
