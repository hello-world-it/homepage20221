<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<title>게시판</title> 

<!-- BBS Style -->
<link href="/asset/BBSTMP_0000000000001/style.css" rel="stylesheet" />
<!-- 공통 Style -->
<link href="/asset/LYTTMP_0000000000000/style.css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 항상 최신버전 사용하기 -->
</head>

<body> 

<div class="wrap"> 
	<form id="frm" name="frm" method="post" action="/join/insertMember.do" onsubmit="return regist();"> <%-- 회원가입의 method="post"★비번입력시 해킹위험방지 --%>
		<input type="hidden" name="loginType" value="${searchVO.loginType }" /> <%-- loginType=normal(일반회원가입) --%>
		<input type="hidden" id="idCheckAt" value="N" /> <%-- html에서만 확인을 위해 name값이 필요X --%>
		
		<table class="mT50 mB50">
			<tbody>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" id="emplyrId" name="emplyrId" /> <button type="button" id="btn-id-check" class="btn">아이디 중복 검사</button>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<input type="text" id="userNm" name="userNm" />
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" id="password" name="password" /></td> <%-- name은 서버에 값을 보내는거(==vo명) / id는 css나 스크립트를 위한 명칭 --%>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td><input type="password" id="password2" /></td> <%-- html에서만 확인을 위해 name값이 필요X / java로 값이 넘어가지X --%>
				</tr>
				<tr>
					<th>비밀번호 힌트</th>
					<td><input type="text" id="passwordHint" name="passwordHint" /></td>
				</tr>
				<tr>
					<th>비밀번호 정답</th>
					<td><input type="text" id="passwordCnsr" name="passwordCnsr" /></td>
				</tr>
			</tbody>
		</table>
		
		<div class="btn-cont ac">
			<button type="submit" class="btn spot">가입</button>
		</div>
	</form>
</div>

<script>
$(document).ready(function(){
	//아이디 중복 검사
	$("#btn-id-check").click(function(){
		var emplyrId = $("#emplyrId").val(); //id값을 받는다
		
		if(emplyrId){
			$.ajax({
				url : "/join/duplicateCheck.do", //비동기 통신시 url을 통해 해당 url로 배난다
				type : "post", //form을 통한 type (get/post)
				data : {"emplyrId" : emplyrId}, //form태그 시 name (input name="emplyrId") { "파라미터로받을명칭" : 파라미터에대한값 }
				dataType : "json", //json이나 html 등으로 받을 시에 반드시 적어줌. 기본은 text
				success : function(data){ //20n 성공  시 //json data를 data라는 변수에 담고
					if(data.successYn == "Y"){ //data에서 data.으로 successYn을 꺼내서 사용 (data값을 받아옴)
						alert("사용가능한 ID입니다.");
						$("#idCheckAt").val("Y");
					}else {
						alert(data.message);
						$("#idCheckAt").val("N");
					}
				}, 
				error : function(){ //40n 등 back에서의 에러를 개발자가 볼 수 있게 (실무에선 error log를 쌓아서 관리)
					alert("error");
				}
			});
		}else {
			alert("ID를 입력해주세요.");
		}
	});
});

//validation 체크 (회원가입, 수강신청 등)
function regist(){
	//아이디 중복 검사 체크
	if($("#idCheckAt").val() != "Y") { //중복검사 시 idCheckAt==Y로 바뀜
		alert("아이디 중복 검사를 해주세요.");
		return false;
	}else if(!$("#emplyrId").val()) {
		alert("아이디를 입력해주세요.");
		return false;
	}else if(!$("#userNm").val()) {
		alert("이름을 입력해주세요.");
		return false;
	}else if(!$("#password").val()) {
		alert("비밀번호를 입력해주세요.");
		return false;
	}else if(!$("#password2").val()) { 
		alert("비밀번호 확인을 입력해주세요.");
		return false;
	}else if(!$("#passwordHint").val()) {
		alert("비밀번호 힌트를 입력해주세요.");
		return false;
	}else if(!$("#passwordCnsr").val()) {
		alert("비밀번호 정답을 입력해주세요.")
		return false;
	}else if($("#password").val() != $("#password2").val()) {
		alert("비밀번호와 비밀번호 확인 정보가 다릅니다.")
		return false;
	}
}

</script>

</body>
</html>






