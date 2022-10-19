package egovframework.let.admin.rsv.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.JsonResponse;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.rsv.service.ReservationApplyService;
import egovframework.let.rsv.service.ReservationApplyVO;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Controller
public class ReservationAdminAppplyController {

	@Resource(name = "reservationApplyService")
	private ReservationApplyService reservationApplyService;
	
	@Resource(name = "reservationService")
	private ReservationService reservationService;
	
	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
	//예약자정보 목록 가져오기
	@RequestMapping(value = "/admin/rsv/selectApplyList.do")
	public String selectApplyList(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "redirect:/index.do"; //Globals.MAIN_PAGE;
		}
		
		//관리자
		searchVO.setMngAt("Y");
		
		List<EgovMap> resultList = reservationApplyService.selectReservationApplyList(searchVO);
		model.addAttribute("resultList", resultList);
		
		// 221017 엑셀 다운로드
		if("Y".equals(searchVO.getExcelAt())) {
			return "admin/rsv/RsvApplySelectListExcel";
		}
		
		return "admin/rsv/RsvApplySelectList";
	}
	
	//예약자정보 상세
	@RequestMapping(value = "/admin/rsv/rsvApplySelect.do")
	public String rsvApplySelect(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "redirect:/index.do"; 
		}else {
			model.addAttribute("USER_INFO", user);
		}
		
		ReservationApplyVO result = reservationApplyService.selectReservationApply(searchVO);
		
		model.addAttribute("result", result);
		
		request.getSession().removeAttribute("sessionReservationApply");
		
		return "admin/rsv/RsvApplySelect";
	}
	
	//예약정보 승인
	@RequestMapping(value = "/admin/rsv/rsvApplyConfirm.do")
	public String updateReservationConfirm(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		//이중 서브밋 방지
		if(request.getSession().getAttribute("sessionReservationApply") != null) {
			return "forward:/admin/rsv/selectApplyList.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "redirect:/index.do";
		}
		
		searchVO.setUserId(user.getId());
		
		reservationApplyService.updateReservationConfirm(searchVO);
		
		//이중 서브밋 방지
		request.getSession().setAttribute("sessionReservationApply", searchVO);
		return "forward:/admin/rsv/selectApplyList.do"; //해당 예약프로그램의 예약자 리스트를 보여주기 위해 forward / redirect 사용 시 모든 프로그램에 대한 예약자가 나온다 
	}
	
	//예약정보 삭제하기
	@RequestMapping(value = "/admin/rsv/rsvApplyDelete.do")
	public String rsvApplyDelete(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if(user == null || user.getId() == null) {
			model.addAttribute("message", "로그인 후 사용가능합니다.");
			return "forward:/index.do"; // + Globals.MAIN_PAGE
		}
		
		searchVO.setUserId(user.getId());
		
		reservationApplyService.deleteReservationApply(searchVO);
		
		return "forward:/admin/rsv/selectApplyList.do";
	}
	
	
	//예약자정보 엑셀 다운로드 (221017)
	@RequestMapping(value= "/admin/rsv/excel.do")
	public ModelAndView excel(@ModelAttribute("searchVO") ReservationApplyVO searchVO, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> columMap = new ArrayList<String>();
		List<Object> valueMap = new ArrayList<Object>();
		String fileName = "";
		
		columMap.add("번호");
		columMap.add("신청자명");
		columMap.add("연락처");
		columMap.add("이메일");
		columMap.add("신청일");
		
		map.put("title", "예약신청현황");
		fileName = EgovStringUtil.getConvertFileName(request, "예약신청현황");
				
		//관리자
		searchVO.setMngAt("Y");
		//목록
		List<EgovMap> resultList = reservationApplyService.selectReservationApplyList(searchVO);
		
		if(resultList != null) {
			EgovMap tmpVO = null;
			Map<String, Object> tmpMap = null;
			for(int i=0; i<resultList.size(); i++) {
				tmpVO = resultList.get(i);
				
				tmpMap = new HashMap<String, Object>();
				tmpMap.put("번호", i+1); //위의 columMap(컬럼명)과 순서 동일
				tmpMap.put("신청자명", tmpVO.get("chargerNm").toString() + "(" + tmpVO.get("frstRegisterId").toString() + ")");
				tmpMap.put("연락처", tmpVO.get("telno").toString());
				tmpMap.put("이메일", tmpVO.get("email").toString());
				tmpMap.put("신청일", tmpVO.get("frstRegistPnttmYmd").toString());
				
				valueMap.add(tmpMap); //map을 리스트화 해서 valueMap에 저장
			}
		}
		
		map.put("columMap", columMap);
		map.put("valueMap", valueMap);
		
		//파일 다운로드 임을 알려줌 (jsp가 아님)
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
		return new ModelAndView("excelDownloadView", "dataMap", map);
	}
	
	//엑셀업로드
	@RequestMapping(value = "/admin/rsv/excelUpload.json", method=RequestMethod.POST) //첨부파일은 무조건 post로 보냄 (통신규칙)
	public @ResponseBody JsonResponse excelUpload(@ModelAttribute ReservationApplyVO searchVO, //@ResponseBody : json으로 보냄  자바v1.8이상 
			ModelMap model, MultipartHttpServletRequest multiRequest, //MultipartHttpServletRequest : 첨부파일 받기
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JsonResponse res = new JsonResponse();
		res.setSuccess(true);
		
		try {
			List<FileVO> result = null;
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if(!files.isEmpty()) {
				result = fileUtil.parseFileInf(files, "TEMP_", 0, null, "rsvFileStorePath"); //엑셀파일 저장 방식 / "rsvFileStorePath" propertier에 설정해놓은 물리경로
				Map<String, Object> resultMap = new HashMap<>(); //json 형식으로 사용하기 위해 map 선언
				
				for(FileVO file : result) { //배열의 크기를 모를 떄, 배열의 크기만큼 for문 사용 시 자주 사용
					if("xls".equals(file.getFileExtsn()) || "xlsx".equals(file.getFileExtsn())) { //xlsx 리눅스(서버)에서 읽을 수 X //업로드 시 파일 확장자 변경X
						searchVO.setCreatIp(request.getRemoteAddr()); 
						resultMap = reservationApplyService.excelUpload(file, searchVO); //ReservationApplyServiceImpl 의  excelUpload 실행하여 resultMap에 넣음 (임플에 정의: 트랜잭션 처리 에러 시 전체rollback을 위해)
						if(!(Boolean)resultMap.get("success")) {
							res.setMessage(String.valueOf(resultMap.get("msg")));
							ArrayList resultList = (ArrayList) resultMap.get("resultList");
							res.setData(resultList);
							res.setSuccess(false);
						}
					}
				}
			}
		} catch (DataAccessException e) {
			res.setMessage(e.getLocalizedMessage());
		}
		return res;
	}
	
}

