package egovframework.let.login.web;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;

import egovframework.let.uat.uia.service.EgovLoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@Resource(name= "loginService")
	private EgovLoginService loginService;
	
	@Resource(name= "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	//로그인 : 파라미터로 id와 pw를 받아
	@RequestMapping(value = "/login/actionLogin.do")
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO,
			HttpServletRequest request, ModelMap model) throws Exception {
		
		//actionLogin에 비밀번호 암호화
		LoginVO resultVO = loginService.actionLogin(loginVO); //받은 값이 로그인 정보와 맞는지 체크 (로그인한 사람의 상세정보) -> 상세정보가 있으면
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) { //LoginVO에 세션을 담는다
			request.getSession().setAttribute("LoginVO", resultVO); 
			return "forward:/index.do"; //forward:post / redirect:get
		}else { //없으면 아이디와 패스워드가 틀렸다는 뜻, 메세지를 뿌려준다
			model.addAttribute("loginMessage", egovMessageSource.getMessage("fail.common.login")); //메세지는 코드로 되어있다
			return "forward:/index.do";
		}
	}
	
	//로그아웃
	@RequestMapping(value = "/login/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {
		
		//정책에 맞게 아래 두가지 중 선택해서 사용
		//RequestContextHolder.getRequestAttributes().removeAttribute("LoginVO", RequestAttributes.SCOPE_SESSION); //로그아웃 시 LoginVO 값만 없애
		request.getSession().invalidate(); //로그아웃 시 사용자가 한 행위의 모든걸 다 날려버림
		
		return "forward:/index.do"; //
	}
}