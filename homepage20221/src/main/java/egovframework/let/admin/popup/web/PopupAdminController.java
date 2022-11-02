package egovframework.let.admin.popup.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.popup.service.PopupService;
import egovframework.let.popup.service.PopupVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class PopupAdminController {

	@Resource(name = "popupService")
	private PopupService popupService;
	
	//팝업 목록 가져오기
	@RequestMapping("/admin/popup/selectList.do")
	public String selectList(@ModelAttribute("searchVO") PopupVO popupVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || EgovStringUtil.isEmpty(user.getId())) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/index.do"; //메인페이지로 이동
		}else {
			model.addAttribute("USER_INFO", user);
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(popupVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(popupVO.getPageUnit());
		paginationInfo.setPageSize(popupVO.getPageSize());
		
		popupVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		popupVO.setLastIndex(paginationInfo.getLastRecordIndex());
		popupVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<EgovMap> resultList = popupService.selectPopupList(popupVO);
		model.addAttribute("resultList", resultList);
		
		int totCnt = popupService.selectPopupListCnt(popupVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "admin/popup/PopupList";
	}
	
	
	//팝업 등록/수정
	@RequestMapping("/admin/popup/regist.do")
	public String regist(@ModelAttribute("searchVO") PopupVO popupVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || EgovStringUtil.isEmpty(user.getId())) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/index.do"; //메인페이지로 이동
		}else {
			model.addAttribute("USER_INFO", user);
		}
		
		EgovMap result = new EgovMap();
		if(!EgovStringUtil.isEmpty(popupVO.getPopupId())) {
			result = popupService.selectPopup(popupVO);
		}
		model.addAttribute("result", result);
		request.getSession().removeAttribute("sessionPopup");
		
		return "admin/popup/PopupRegist";
	}
	
	//팝업 등록
	@RequestMapping("/admin/popup/insert.do")
	public String insert(@ModelAttribute("searchVO") PopupVO popupVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || EgovStringUtil.isEmpty(user.getId())) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/index.do"; //메인페이지로 이동
		}else {
			model.addAttribute("USER_INFO", user);
			popupVO.setUserId(user.getId());
		}
		
		popupService.insertPopup(popupVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionPopup", popupVO);
		
		return "forward:/admin/popup/selectList.do";
	}
	
	//팝업 수정하기
	@RequestMapping("/admin/popup/update.do")
	public String update(@ModelAttribute("searchVO") PopupVO popupVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || EgovStringUtil.isEmpty(user.getId())) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/index.do"; //메인페이지로 이동
		}else {
			model.addAttribute("USER_INFO", user);
			popupVO.setUserId(user.getId());
		}
		
		popupService.updatePopup(popupVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionPopup", popupVO);
			
		return "forward:/admin/popup/selectList.do";
	}
	
	//팝업 삭제하기
	@RequestMapping("/admin/popup/delete.do")
	public String delete(@ModelAttribute("searchVO") PopupVO popupVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || EgovStringUtil.isEmpty(user.getId())) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/index.do"; //메인페이지로 이동
		}else {
			model.addAttribute("USER_INFO", user);
			popupVO.setUserId(user.getId());
		}
		
		popupService.deletePopup(popupVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionPopup", popupVO);
			
		return "forward:/admin/popup/selectList.do";
	}
}
