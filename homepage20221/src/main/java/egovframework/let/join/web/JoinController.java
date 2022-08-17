package egovframework.let.join.web;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.join.service.JoinService;
import egovframework.let.join.service.JoinVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import net.sf.json.JSONObject;

@Controller
public class JoinController {

	@Resource(name="joinService")
	private JoinService joinService;
	
	@Resource(name="egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	//회원구분
	@RequestMapping(value="/join/memberType.do") //페이지 들어와서 파라미터값만 받아서 return
	private String memberType(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model) throws Exception {

		return "join/MemberType";
	}
	
	//회원등록 폼
	@RequestMapping(value="/join/memberRegist.do")
	private String memberRegist(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model) throws Exception {

		return "join/MemberRegist";
	}
	
	//회원가입
	@RequestMapping(value="/join/insertMember.do") //등록폼에서 작성한걸 가지고 회원가입이 이루어진다
	private String insertMember(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, ModelMap model) throws Exception {

		if(!EgovStringUtil.isEmpty(vo.getLoginType())) {
			//일반가입을 제외하고는 ID값은 SNS명 + ID값 (SNS 사용 시 -> ex. KAKAO-123456 )
			if(!("normal").equals(vo.getLoginType())) {
				vo.setEmplyrId(vo.getLoginType() + "-" + vo.getEmplyrId());
				vo.setPassword(""); //SNS가입자는 비번이 필요 없어. SNS로 로그인 하니깐. not null이라 넣어줌
				vo.setPasswordHint("SNS가입자"); //SNS가입자는 비번을 찾을 일이 없어
				vo.setPasswordCnsr("SNS가입자");
			}
		}
		
		if(joinService.duplicateCheck(vo) > 0) { //일반회원가입
			model.addAttribute("message", egovMessageSource.getMessage("fail.duplicate.member")); //이미 사용중인 ID입니다.
			return "forward:/join/memberType.do";
		}else {
			joinService.insertJoin(vo); //회원가입 완료 후 로그인화면으로 
			model.addAttribute("message", egovMessageSource.getMessage("join.request.msg")); //회원신청이 정상적으로 완료되었습니다. 로그인 후 이용해 주세요.
		}
		
		return "forward:/index.do";
		
	}
	
	//아이디 중복체크
	@RequestMapping(value="/join/duplicateCheck.do")
	public void duplicateCheck(@ModelAttribute("searchVO") JoinVO vo, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		String successYn = "Y";
		String message = "성공";
		
		JSONObject jo = new JSONObject();
		response.setContentType("application/json; charset=utf-8");
		
		int duplicateCnt = joinService.duplicateCheck(vo); 
		if(duplicateCnt > 0) { //중복일 경우 1 (이미 가입한 아이디가 하나 있음) / 마일리지 관리(날짜를 체크해서), 적립금 관리(당일 쿠폰 사용 시) 시 응용해서 사용 
			successYn = "N";
			message = egovMessageSource.getMessage("fail.duplicate.member"); //이미 사용중인 ID입니다.
		}
		
		jo.put("successYn", successYn);
		jo.put("message", message);
		
		PrintWriter printwriter = response.getWriter();
		printwriter.println(jo.toString());
		printwriter.flush();
		printwriter.close();
	}
		
}
