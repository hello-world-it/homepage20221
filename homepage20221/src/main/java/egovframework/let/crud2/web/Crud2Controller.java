package egovframework.let.crud2.web;
import java.util.List; ////선언을 했는데 쓰지 않으명 노란줄이 생김

import egovframework.let.crud2.service.Crud2Service;
import egovframework.let.crud2.service.Crud2VO;
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
public class Crud2Controller {
	
	////상속을 받아 사용
	@Resource(name= "crud2Service") ////name은 ServiceImpl의 @Service("crudService") 값과 동일해야 호출이 가능
	private Crud2Service crud2Service;
  //private   클래스객체명           변수명;  	//클래스객체명은 앞글자 대문자, (내가앞으로사용할)변수명 앞글자 소문자
	
	//데이터 목록 가져오기
	@RequestMapping(value = "/crud2/selectList.do")
	public String selectList(@ModelAttribute("searchVO") Crud2VO searchVO,
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
		
		int totCnt = crud2Service.selectCrudListCnt(searchVO);
		//총 갯수
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		//목록 가져오기
		List<EgovMap> resultList = crud2Service.selectCrudList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		//
			
		return "crud2/Crud2SelectList"; //jsp페이지
	}
		
	//데이터 등록/수정  
	@RequestMapping(value = "/crud2/crudRegist.do") //주소로 갔을 때(SelectList에서) 해당 소스가 실행
	public String crudRegist(@ModelAttribute("searchVO") Crud2VO crud2VO, //url을 사용자가 쓸 수 있게 만들어준다
			HttpServletRequest request, ModelMap model) throws Exception {
		
			//id값 확인해서 등록을 할지 수정을 할지 체크
			Crud2VO result = new Crud2VO();
			if(!EgovStringUtil.isEmpty(crud2VO.getCrud2Id())) {
				result=crud2Service.selectCrud(crud2VO);
			} //수정
			model.addAttribute("result", result); //둥록
			//
			
				return "crud2/Crud2Regist";
			} 
	
	//데이터 등록하기
	@RequestMapping(value="/crud2/insert.do")
	public String insert(@ModelAttribute("searchVO") Crud2VO searchVO,
		HttpServletRequest request, ModelMap model) throws Exception {
			
		crud2Service.insertCrud(searchVO);
		return "forward:/crud2/selectList.do";
	}
	
	//데이터 가져오기 (상세페이지)
	@RequestMapping(value = "/crud2/select.do") ////value = localhost url (localhost/temp/selecet.do로 오면 아래의 메소드를 싱행시켜 브라우저에 보여준다)
	public String select(@ModelAttribute("searchVO") Crud2VO searchVO, 	  //메소드 선언 searchVO가 CrudVO를 불러와서 사용
			HttpServletRequest request, ModelMap model) throws Exception{
		
		Crud2VO result = crud2Service.selectCrud(searchVO);
		model.addAttribute("result", result); ////매핑
		return "crud2/Crud2Select"; 
	}	
	
	//데이터 수정하기
	@RequestMapping(value="/crud2/update.do")
	public String update(@ModelAttribute("searchVO") Crud2VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		crud2Service.updateCrud(searchVO);
		return "forward:/crud2/selectList.do";
	}
	
	//데이터 삭제하기
	@RequestMapping(value="/crud2/delete.do")
	public String delete(@ModelAttribute("searchVO") Crud2VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		crud2Service.deleteCrud(searchVO);
		return "forward:/crud2/selectList.do";
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