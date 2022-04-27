package egovframework.let.temp2.web;
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
//import egovframework.let.temp.service.TempService;
//import egovframework.let.temp.service.TempVO;
import egovframework.let.temp2.service.Temp2Service;
import egovframework.let.temp2.service.Temp2VO;
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
public class Temp2Controller {
	
	////상속을 받아 사용
	@Resource(name= "temp2Service") ////name은 ServiceImpl의 @Service("tempService") 값과 동일해야 호출이 가능
	private Temp2Service temp2Service;
	
	//임시데이터 가져오기
	//어노테이션은 프로젝트 당 유일
	@RequestMapping(value = "/temp2/select.do") ////value = localhost url (localhost/temp/selecet.do로 오면 아래의 메소드를 싱행시켜 브라우저에 보여준다)
	public String select(@ModelAttribute("searchVO") Temp2VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception{
		
		Temp2VO result = temp2Service.selectTemp(searchVO);
		model.addAttribute("result", result); ////매핑
		return "temp2/TempSelect"; 
	}
	
	//임시데이터 목록 가져오기
	@RequestMapping(value = "/temp2/selectList.do")
	public String selectList(@ModelAttribute("searchVO") Temp2VO searchVO,
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
		
		int totCnt = temp2Service.selectTempListCnt(searchVO);
		//총 갯수
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		//목록 가져오기
		List<EgovMap> resultList = temp2Service.selectTempList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		//
		
		return "temp2/TempSelectList"; 
	}	
	
	//임시데이터 등록/수정 - 
	@RequestMapping(value = "/temp2/tempRegist.do")
	public String tempRegist(@ModelAttribute("searchVO") Temp2VO tempVO, //url을 사용자가 쓸 수 있게 만들어준다
			HttpServletRequest request, ModelMap model) throws Exception {
		
			//
			Temp2VO result = new Temp2VO();
			if(!EgovStringUtil.isEmpty(tempVO.getTempId())) { //여기의 tempVO는 명칭
				result=temp2Service.selectTemp(tempVO);
			}
			model.addAttribute("result", result);
			//
			
				return "temp2/TempRegist";
			} 
	
	//임시데이터 등록하기
	@RequestMapping(value="/temp2/insert.do")
	public String insert(@ModelAttribute("searchVO") Temp2VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
				temp2Service.insertTemp(searchVO);
				return "forward:/temp2/selectList.do";
		}
	
	//임시데이터 수정하기
	@RequestMapping(value="/temp2/update.do")
	public String update(@ModelAttribute("searchVO") Temp2VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		temp2Service.updateTemp(searchVO);
		return "forward:/temp2/selectList.do";
	}
	
	//임시데이터 삭제하기
	@RequestMapping(value="/temp2/delete.do")
	public String delete(@ModelAttribute("searchVO") Temp2VO searchVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		temp2Service.deleteTemp(searchVO);
		return "forward:/temp2/selectList.do";
	}
	
}
