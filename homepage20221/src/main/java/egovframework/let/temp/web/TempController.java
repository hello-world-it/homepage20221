package egovframework.let.temp.web;
import java.util.List; ////선언을 했는데 쓰지 않아서 노란줄이 생김_신경X
import java.util.Map;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.cop.bbs.service.BoardMaster;
import egovframework.let.cop.bbs.service.BoardMasterVO;
import egovframework.let.cop.bbs.service.EgovBBSAttributeManageService;
import egovframework.let.temp.service.TempService;
import egovframework.let.temp.service.TempVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

@Controller ////@어노테이션
public class TempController {
	
	////상속을 받아 사용
	@Resource(name= "tempService") ////name은 ServiceImpl의 @Service("tempService") 값과 동일해야 호출이 가능
	private TempService tempService;
	
	//임시데이터 가져오기
	@RequestMapping(value = "/temp/select.do") ////value = localhost url (localhost/temp/selecet.do로 오면 아래의 메소드를 싱행시켜 브라우저에 보여준다)
	public String select(@ModelAttribute("searchVO") TempVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		TempVO result = tempService.selectTemp(searchVO);
		model.addAttribute("result", result); ////매핑
		return "temp/TempSelect"; 
	}
	
	//임시데이터 목록 가져오기
	@RequestMapping(value = "/temp/selectList.do")
	public String selectList(@ModelAttribute("searchVO") TempVO searchVO,
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
		
		int totCnt = tempService.selectTempListCnt(searchVO);
		//총 갯수
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		//목록 가져오기
		List<EgovMap> resultList = tempService.selectTempList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		//
		
		return "temp/TempSelectList"; 
	}	
	
	//임시데이터 등록/수정 - 
	@RequestMapping(value = "/temp/tempRegist.do")
	public String tempRegist(@ModelAttribute("searchVO") TempVO tempVO, //url을 사용자가 쓸 수 있게 만들어준다
			HttpServletRequest request, ModelMap model) throws Exception {
		
			//
			TempVO result = new TempVO();
			if(!EgovStringUtil.isEmpty(tempVO.getTempId())) {
				result=tempService.selectTemp(tempVO);
			}
			model.addAttribute("result", result);
			//
			
				return "temp/TempRegist";
			} 
	
	//임시데이터 등록하기
	@RequestMapping(value="/temp/insert.do")
	public String insert(@ModelAttribute("searchVO") TempVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
				tempService.insertTemp(searchVO);
				return "forward:/temp/selectList.do";
		}
	
	//임시데이터 수정하기
	@RequestMapping(value="/temp/update.do")
	public String update(@ModelAttribute("searchVO") TempVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		tempService.updateTemp(searchVO);
		return "forward:/temp/selectList.do";
	}
	
	//임시데이터 삭제하기
	@RequestMapping(value="/temp/delete.do")
	public String delete(@ModelAttribute("searchVO") TempVO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		tempService.deleteTemp(searchVO);
		return "forward:/temp/selectList.do";
	}
	
	//JSTL / 220420  폴더 위치 생각하기...
	//어노테이션에 url?? 
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
	}
	
	
}
