<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<title><c:out value='${result.popupTitleNm}'/></title> 

<script src="http://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 항상 최신버전 사용하기 -->
<script src="/asset/cookie.js"></script>

<script>
//체크버튼 클릭시
$(document).ready(function(){
	$("#chkPopup").change(function(){
		if($(this).is(":checked")) { //쿠키에 set
			$.cookie("${result.popupId}", "done", {expires:1,path : '/'}); //popupId를 가지고 올 때 done이 있으면 24시간 안보게 해줌. espires:1 (1일) path:'/' (사용자 url 다음부터 사용)
		}else {
			$.removeCookie("${result.popupId}", {expires:1,path : '/'}); //체크안될땐 set한거 해제
		}
	});
});

function fnPopupCheck() {
	
	var chk = document.getElementById("chkPopup");
	if(chk && chk.checked) {
		$.cookie("${result.popupId}", "done", {expires:1,path : '/'});
	}
	window.close();
}
</script>

<style>
	html {height: 100%; padding: 0; margin: 0;}
	body {width: 100%; height: 100%; overflow: auto; padding: 0; margin: 0;}
	#popmainboxdn {position: fixed; bottom: 0; clear: both; width: 100%; height: 30px; padding: 0;
					background-color: #747474; text-align: right; line-height: 30px;}
	#popmainboxdn label {margin-right: 20px; padding-top: 4px; color: #d8d8d8;}
	#popmainboxdn input {border:0; margin-right: 5px; vertical-align: middle;}
	
</style>
</head>

<body> 
<div id="popmainboxcon">
	<div id="popmainboxcon2">
		<div id="popmainboxcontext">
			<c:out value="${result.popupCn}" escapeXml="false"/>
		</div>
	</div>
</div>

<div id="popmainboxdn">
	<label for="chkPopup">
		<input type="checkbox" name="chkPopup" id="chkPopup" />24시간동안 이 창 열지 않기
	</label>
	<a href="#" class="btn_close_pop" onclick="fnPopupCheck()" title="팝업창닫기">
		<img alt="닫기" src="/asset/LYTTMP_0000000000000/images/common/popbox_close.gif">
	</a>
</div>
</body>
</html>