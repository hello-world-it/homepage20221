package egovframework.let.crud.web;
import java.util.List; ////선언을 했는데 쓰지 않으명 노란줄이 생김


import egovframework.let.crud.service.CrudService;
import egovframework.let.crud.service.CrudVO;
//import egovframework.let.temp.service.TempService;
//import egovframework.let.temp.service.TempVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller ////@어노테이션
public class CrudController {
	
	////상속을 받아 사용
	@Resource(name= "crudService") ////name은 ServiceImpl의 @Service("crudService") 값과 동일해야 호출이 가능
	private CrudService crudService;
  //private   클래스객체명           변수명;  	//클래스객체명은 앞글자 대문자, (내가앞으로사용할)변수명 앞글자 소문자
	
	//데이터 목록 가져오기
	@RequestMapping(value = "/crud/selectList.do")
	public String selectList(@ModelAttribute("searchVO") CrudVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		/* 목록만 가져오기
		List<EgovMap> resultList = tempService.selectTempList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		*/
		
		//전자정부의 PaginationInfo를 가져다 쓴다 (라이브러리 활용) / 220420 Ctrl클릭해서 확인가능
		//currentPageNo : 현재 페이지 번호 / recordCountPerPage : 한 페이지당 게시되는 게시물 건 수
		//pageSize : 페이지 리스트에 게시되는 페이지 건수 / totalRecordCount : 전체 게시물 건 수 
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		//파라미터를 받는다
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		int totCnt = crudService.selectCrudListCnt(searchVO);
		//총 갯수
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		//목록 가져오기
		List<EgovMap> resultList = crudService.selectCrudList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		//
			
		return "crud/CrudSelectList"; //jsp페이지
	}
		
	//데이터 등록/수정  
	@RequestMapping(value = "/crud/crudRegist.do") //주소로 갔을 때(SelectList에서) 해당 소스가 실행
	public String crudRegist(@ModelAttribute("searchVO") CrudVO crudVO, //url을 사용자가 쓸 수 있게 만들어준다
			HttpServletRequest request, ModelMap model) throws Exception {
		
			//id값 확인해서 등록을 할지 수정을 할지 체크
			CrudVO result = new CrudVO();
			if(!EgovStringUtil.isEmpty(crudVO.getCrudId())) {
				result=crudService.selectCrud(crudVO);
			} //수정
			model.addAttribute("result", result); //둥록
			//
			
				return "crud/CrudRegist";
			} 
	
	//데이터 등록하기
	@RequestMapping(value="/crud/insert.do")
	public String insert(@ModelAttribute("searchVO") CrudVO searchVO,
		HttpServletRequest request, ModelMap model) throws Exception {
			
		crudService.insertCrud(searchVO);
		return "forward:/crud/selectList.do";
	}
	
	//데이터 가져오기 (상세페이지)
	@RequestMapping(value = "/crud/select.do") ////value = localhost url (localhost/temp/selecet.do로 오면 아래의 메소드를 싱행시켜 브라우저에 보여준다)
	public String select(@ModelAttribute("searchVO") CrudVO searchVO, 	  //메소드 선언 searchVO가 CrudVO를 불러와서 사용
			HttpServletRequest request, ModelMap model) throws Exception{
		
		CrudVO result = crudService.selectCrud(searchVO);
		model.addAttribute("result", result); ////매핑
		return "crud/CrudSelect"; 
	}	
	
	//데이터 수정하기
	@RequestMapping(value="/crud/update.do")
	public String update(@ModelAttribute("searchVO") CrudVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		crudService.updateCrud(searchVO);
		return "forward:/crud/selectList.do";
	}
	
	//데이터 삭제하기
	@RequestMapping(value="/crud/delete.do")
	public String delete(@ModelAttribute("searchVO") CrudVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		crudService.deleteCrud(searchVO);
		return "forward:/crud/selectList.do";
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