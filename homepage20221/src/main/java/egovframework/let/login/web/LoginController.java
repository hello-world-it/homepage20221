package egovframework.let.login.web;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.let.api.naver.service.NaverLoginService;
import egovframework.let.join.service.JoinService;
import egovframework.let.join.service.JoinVO;
import egovframework.let.uat.uia.service.EgovLoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;

@Controller
public class LoginController {
	
	@Resource(name= "loginService")
	private EgovLoginService loginService;
	
	@Resource(name= "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	// 220907  네이버로그인
	@Resource(name= "naverLoginService")
	private NaverLoginService naverLoginService;
	
	@Resource(name= "joinService")
	private JoinService joinService;
	
	
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
	
	// 220907 네이버 로그인 콜백
	@RequestMapping(value = "/login/naverLogin.do")						//@RequestParam : 값이 안오면 차단 시키고 이후 진행을 X (필수값)
	public String naverLogin(@ModelAttribute("loginVO") LoginVO loginVO, @RequestParam String code, @RequestParam String state, HttpSession session, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		String domain = request.getServerName();
		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginService.getAccessToken(session, code, state, domain); //토큰생성
		
		//로그인 사용자 정보를 읽어온다 (위에서 생성한 토큰을 이용)
		String apiResult = naverLoginService.getUserProfile(oauthToken);
		
		JSONParser parser = new JSONParser(); //json을 파싱
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		JSONObject result = (JSONObject) jsonObj.get("response"); //pom.xml(json-simple)에서 추가한 라이브러리 사용
													//response -> 네이버 / properties -> 카카오
		
		loginVO.setId("NAVER-" + result.get("id").toString());
		loginVO.setPassword("");
		loginVO.setUserSe("USR");
		
		LoginVO resultVO = loginService.actionLogin(loginVO);
		//로그인 값이 없으면 회원가입처리
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			request.getSession().setAttribute("LoginVO", resultVO);
			return "forward:/index.do";
		}else {
			//일반가입을 제외하고는 ID값은 SNS명+ID값
			JoinVO joinVO = new JoinVO();
			joinVO.setEmplyrId(loginVO.getId());
			joinVO.setUserNm(result.get("name").toString()); //name은 nickName과 별개
			joinVO.setPassword("");
			joinVO.setPasswordHint("SNS가입자");
			joinVO.setPasswordCnsr("SNS가입자");
			
			joinService.insertJoin(joinVO);
			model.addAttribute("loginMessage", "회원가입이 완료되었습니다.");
			
			return "forward:/index.do";
		}
		
	}
}

