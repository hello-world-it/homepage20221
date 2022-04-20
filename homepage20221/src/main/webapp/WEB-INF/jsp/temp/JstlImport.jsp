<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko">
<title>JSTL</title> 
</head>

<body> 
	JSTL Import 하기~ <br/>
	
	<!-- Jstl.jsp에서 선언한 c:param을 불러서 쓴다. http://localhost/temp/jstlImport.do?test=값 ->'값'이 화면에 출력  -->
	<c:out value="${param.test}" />
</body>
</html>