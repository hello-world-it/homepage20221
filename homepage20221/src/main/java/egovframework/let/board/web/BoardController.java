package egovframework.let.board.web;
import java.util.List; ////선언을 했는데 쓰지 않으면 노란줄이 생김

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
	
	////상속을 받아 사용
	@Resource(name= "boardService") ////name은 ServiceImpl의 @Service("crudService") 값과 동일해야 호출이 가능
	private BoardService boardService;
  //private   클래스객체명           변수명;  	//클래스객체명은 앞글자 대문자, (내가앞으로사용할)변수명 앞글자 소문자
	
	//1.게시물 목록 가져오기
	@RequestMapping(value = "/board/selectList.do")
	public String selectList(@ModelAttribute("searchVO") BoardVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		/* 목록만 가져오기
		List<EgovMap> resultList = tempService.selectTempList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		*/
		
//		전자정부의 PaginationInfo를 가져다 쓴다 (라이브러리 활용) / 220420 Ctrl클릭해서 확인가능
//		currentPageNo : 현재 페이지 번호 / recordCountPerPage : 한 페이지당 게시되는 게시물 건 수
//		pageSize : 페이지 리스트에 게시되는 페이지 건수 / totalRecordCount : 전체 게시물 건 수 
		
		//220518
		searchVO.setNoticeAt("Y");
		List<EgovMap> noticeResultList = boardService.selectBoardList(searchVO);
		model.addAttribute("noticeResultList", noticeResultList);
		//
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		//파라미터를 받는다
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		//220518
		searchVO.setNoticeAt("N");
		List<EgovMap> resultList = boardService.selectBoardList(searchVO);
		model.addAttribute("resultList", resultList);
		//
		
		int totCnt = boardService.selectBoardListCnt(searchVO);
		//총 갯수
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		/* 목록 가져오기
		List<EgovMap> resultList = boardService.selectBoardList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		*/
		
		//220518
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		//
			
		return "board/BoardSelectList"; //jsp페이지
	}
		

	
	//JSTL / 220420  폴더 위치 생각하기...
/*	//어노테이션에 url?? 
	@RequestMapping(value="/temp/jstl.do")
	public String jstl(@ModelAttribute("searchVO") TempVO searchVO, 
			HttpServletRequest request, ModelMap model) throws Exception {
		return "/temp/Jstl";
	} //temp폴더에 Jstl.jsp
	
	//JSTL Import용
	@RequestMapping(value="/temp/jstlImport.do")
	public String jstlImport(@ModelAttribute("searchVO") TempVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		return "/temp/JstlImport";
	}*/	
}