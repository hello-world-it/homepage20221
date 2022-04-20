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

<c:set var="step" value="${param.step}" />
<%-- ↓step이라는 파라미터가 없으면step은 1 --%>
<c:if test="${empty step}"> 
	<c:set var="step" value="1" />
</c:if>

<ul>
	<c:choose>
	<%-- c(core) tag 설명 --%>
		<%-- c:out은 print역할 --%>
		<c:when test="${step eq '1'}">
			<li>
				<h3>c:out Tag : &#60;%= ...%&#62;과 유사한 표현식</h3>
				<h4>기본문법: &#60;c:out value="값"/&#62;</h4>
				
				JSP 1번 : <% out.print("hello~"); %> <br/> 
				JSP 2번 : <% String a = "Hello~!!"; %><%= a %> <br/>
				
				JSTL : <c:out value="hello~~" /><br/>
			</li>
		</c:when>
		
		<%-- c:import / 주로 메뉴나 헤더,푸터 등을 import해서 사용 --%>
		<c:when test="${step eq '2'}">
			<li>
				<h3>c:import Tag : jsp 'include'와 유사. 서버 내부 또는 서버 외부의 모든 자원 컨텐츠를 포함하는 추가 기능이 있음 </h3>
				<h4>기본문법 : &#60;c:import url="URL주소값" charEncoding="utf-8"/&#62;</h4>
				
				<c:import url="/temp/jstlImport.do" charEncoding="utf-8" />		
				<%-- <c:import url="/temp/selectList.do" charEncoding="utf-8" /> utf-8은 공용, 이모지 사용을 위해 utf8mb4 사용이 늘어나는 중 --%>	
			</li>
		</c:when>
		
		<%-- c:set --%>
		<c:when test="${step eq '3'}">
			<li>
				<h3>c:set Tag : 일반 변수를 생성해서 값을 할당</h3>
				<h4>기본문법 : &#60;c:set var="변수명" value="값"/&#62;</h4>
				
				<c:set var="str" value="지금은 c:set 연습중" />
				<c:out value="${str}" />
			</li>
		</c:when>
		
		<%-- c:if --%>
		<c:when test="${step eq '4'}">
			<li>
				<h3>c:if Tag : 조건문 중의 하나. JAVA에서 사용하는 if문과 동일</h3>
				<h4>기본문법 : &#60;c:if test='조건문'&#62; &#60;/c:if&#62;</h4>
				
				<c:set var="test" value="0" />
				<c:if test="${test eq '0'}" > true </c:if>
			</li>
		</c:when>
		
		<%-- c:choose, c:when, c:otherwise --%>
		<c:when test="${step eq '5'}">
			<li>
				<h3>c:choose, c:when, c:otherwise Tag : 조건문 중의 하나. JAVA에서 사용하는 if, else if, else와 비슷 함</h3>
				<h4>기본문법 : <br/>
					&#60;c:choose&#62; <br/>
						&#60;c:when test="조건문"&#62; 조건 true에 대한 내용 &#60;/c:when&#62;<br/>
						&#60;c:otherwise&#62;조건에 해당되지 않아서 나오는 내용&#60;/c:otherwise&#62; //마지막에 들어간다. JAVA의 else처럼<br/>
					&#60;/c:choose&#62;
				</h4>
				
				<c:set var="test" value="2" />
				<c:choose>
					<c:when test="${test eq '1'}">1입니다</c:when>
					<c:when test="${test eq '2'}">2입니다</c:when>
					<c:otherwise>조건에 해당되지 않음</c:otherwise>
				</c:choose>
			</li>
		</c:when>
		
		<%-- c:forEach --%>
		<c:when test="${step eq '6'}">
			<li>
				<h3>c:forEach Tag : 기본 반복 태그, 고정 된 횟수 또는 초과 수집 동안 중첩 된 본문 내용을 반복</h3>
				<h4>기본문법 : <br/>
					&#60;c:forEach var="변수명" items="반복문 변수"&#62;<br/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;반복문 내용<br/>
					&#60;/c:forEach&#62;
				</h4>
				
				<c:set var="str" value="1,2,3,4,5,6" />
				<c:set var="strSplit" value="${fn:split(str, ',')}" /> <%-- fn:split(str, ',') str의 문자열을 ','기준으로 나누어진 배열로 반환 --%>
				<c:forEach var="result" items="${strSplit}">
					<c:out value="${result}" /><br/>
				</c:forEach>
			</li>
		</c:when>
	
		<%-- c:param / paramiter --%>
		<c:when test="${step eq '7'}">
			<li>
				<h3>c:param Tag : 포함하는 import, url 태그의 URL에 매개변수를 추가</h3>
				<h4>기본문법 : &#60;c:param name="변수명" value="값"/&#62;</h4>
				
				<c:import url="/temp/jstlImport.do" charEncoding="utf-8">
					<c:param name="test" value="테스트 호출" />
				</c:import>
			</li>
		</c:when>
		
		<%-- c:url --%>
		<c:when test="${step eq '8'}">
			<li>
				<h3>c:url Tag : url 주소</h3>
				<h4>기본문법 : &#60;c:url var="변수명" value="url주소"/&#62;</h4>
				
				URL1 : <c:url var="link1" value="https://www.naver.com" />
				URL2 : <c:url var="link2" value="/temp/jstlImport.do">
							<c:param name="test" value="테스트 호출" />
					   </c:url>
					   <br/>
					   
				<a href="${link1}" target="_blank" title="네이버로새창열기">링크1번</a><br/> <%-- 외부사이트 이동  target="_blank"시 title="새창열기" 꼭 필요! 웹접급성--%>
				<a href="${link2}" target="_blank">링크2번</a>
			</li>
		</c:when>
		
	</c:choose>
</ul>

</body>
</html>