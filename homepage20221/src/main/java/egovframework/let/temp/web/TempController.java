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
		
		List<EgovMap> resultList = tempService.selectTempList(searchVO); //List<변수> 변수명 = 가져올정보
		model.addAttribute("resultList", resultList); //model에 resultList를 담아서 뿌려줌
		
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
	
	
}
