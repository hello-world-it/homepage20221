package egovframework.let.admin.rsv.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class ReservationAdminController {

	@Resource(name= "reservationService")
	private ReservationService reservationService;
	
	//예약정보 목록 가져오기
	@RequestMapping(value= "/admin/rsv/rsvSelectList.do")
	public String rsvSelectList(@ModelAttribute("searchVO") ReservationVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		List<EgovMap> resultList = reservationService.selectReservationList(searchVO); //리스트
		model.addAttribute("resultList", resultList);
		
		int totCnt = reservationService.selectReservationListCnt(searchVO); //카운트
		
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("USER_INFO", user);
		
		return "admin/rsv/RsvSelectList";
	}
	
	// 220921
	//예약정보 등록/수정
	@RequestMapping(value = "/admin/rsv/rsvRegist.do")
	public String revRegist(@ModelAttribute("searchVO") ReservationVO ReservationVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		//로그인 여부 및 권한체크
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser(); 
		if(user == null) { //로그인 여부만 체크 -> 실무에선 관리자 권한이 있는 사람만 관리자페이지에 들어올 수 있게 권한체크 필수!
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/admin/rsv/rsvSelectList.do";
		}else {
			model.addAttribute("USER_INFO", user);
		}
		
		//아이디 정보가 있으면 가지고 와서 수정페이지로
		ReservationVO result = new ReservationVO(); //정보를 불러옴
		if(!EgovStringUtil.isEmpty(ReservationVO.getResveId())) {
			result = reservationService.selectReservation(ReservationVO);
		}
		model.addAttribute("result", result);
		
		request.getSession().removeAttribute("sessionReservation");
		
		return "admin/rsv/RsvRegist";
	}
	
	//예약정보 등록하기
	@RequestMapping(value = "/admin/rsv/rsvInsert.do")
	public String rsvInsert(@ModelAttribute("searchVO") ReservationVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		//이중 서브밋 방지 체크
		if(request.getSession().getAttribute("sessionReservation") != null) {
			return "forward:/admin/rsv/rsvSelectList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다");
			return "forward:/admin/rsv/rsvSelectList.do";
		}
		
		searchVO.setUserId(user.getId());
		
		reservationService.insertReservation(searchVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionReservation", searchVO);
		return "forward:/admin/rsv/rsvSelectList.do";
	}
	
	//예약정보 수정하기
	@RequestMapping(value = "/admin/rsv/rsvUpdate.do")
	public String rsvUpdate(@ModelAttribute("searchVO") ReservationVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		//이중 서브밋 방지
		if(request.getSession().getAttribute("sessionReservation") != null) {
			return "forward:/admin/rsv/rsvSelectList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();	
		if(user == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/admin/rsv/rsvSelectList.do";
		}
		
		searchVO.setUserId(user.getId());
		
		reservationService.updateReservation(searchVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionReservation", searchVO);
		return "forward:/admin/rsv/rsvSelectList.do";
	}

	//예약정보 삭제하기
	@RequestMapping(value = "/admin/rsv/rsvDelete.do")
	public String rsvDelete(@ModelAttribute("searchVO") ReservationVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/admin/rsv/rsvSelectList.do";
		}
		searchVO.setUserId(user.getId());
		
		reservationService.deleteReservation(searchVO);
		
		return "forward:/admin/rsv/rsvSelectList.do";
	}
	
}

