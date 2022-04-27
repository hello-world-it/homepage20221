<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		
	<%-- function tag 설명 --%>
		<%-- fn:contains --%>
		<c:when test="${step eq '9'}">
			<li>
				<h3>fn:contains() : 주어진 문자열이 특정 문자열을 포함하고 있는지 확인하는데 사용</h3>
				
				<c:set var="str" value="지금은fn:contains 연습중" />
				<c:if test="${fn:contains(str, '지금')}"> true </c:if>
				<c:if test="${fn:contains(str, '안녕')}"> true </c:if> 
			</li>
		</c:when>
		
		<%-- fn:indexOf --%>
		<c:when test="${step eq '10'}">
			<li>
				<h3>fn:indexOf() : 주어진 문자열이 특정 문자열로 끝나는지 확인하는데 사용 (fn:contains()와 유사하지만 더 많이 사용됨)</h3>
				
				<c:set var="string" value="It is first String." />
				<p>Index (first) : ${fn:indexOf(string, "first")} (first가 6번째에 나온다, 0부터 시작. if문 사용해서 -1보다 크다로 해당 단어가 있나 확인 할 때 주로 사용)</p>
				<p>Index (is) : ${fn:indexOf(string, "is")}</p>
			</li>
		</c:when>
		
		<%-- fn:startsWith --%>
		<c:when test="${step eq '11'}">
			<li>
				<h3>fn:startsWith() : 주어진 문자열이 특정 문자열 값으로 시작되는지 확인하는데 사용</h3>
				
				<c:set var="String" value="Welcome to JSP programming" />
				<c:if test="${fn:startsWith(String, 'Welcome')}"> <%-- 대소문자 구분 꼭! --%>
					<p>String starts with 'Welcome'</p>
				</c:if>
				
				<c:if test="${fn:startsWith(String, 'programming')}">
					<p>String starts with 'programming'</p>
				</c:if>
				
				<%-- fn:endsWith --%>
				<c:if test="${fn:endsWith(String, 'programming')}">
					<p>String ends with 'programming'</p>
				</c:if>
				
				<c:set var="String" value="https://www.sj.com" /> 
				<c:if test="${fn:startsWith(String, 'https://')}"> 
					<p>실서버(개발용은http 실서버는https(보안))체크 or 도메인 체크 시 자주 사용</p>
				</c:if>
			</li>
		</c:when>
		
		<%-- fn:endsWith --%>
		<c:when test="${step eq '12'}">
			<li>
				<h3>fn:endsWith() : 주어진 문자열이 특정 문자열 값으로 끝나는지 확인하는데 사용</h3>
				
				<c:set var="String" value="Welcome to JSP programming" />
				<c:if test="${fn:endsWith(String, 'programming')}">
					<p>String ends with 'programming'</p>
					<p>확장자 찾을 때 자주 사용</p>
				</c:if>
				
				<c:if test="${fn:endsWith(String, 'JSP')}">
					<p>String ends with 'JSP'</p>
				</c:if>
			</li>
		</c:when>
		
		<%-- fn:split --%>
		<c:when test="${step eq '13'}">
			<li>
				<h3>fn:split() : 주어진 문자열을 특정 문자로 구분해서 배열로 분할</h3>
				
				<c:set var="str" value="1,2,3,4,5,6" />
				<c:set var="strSplit" value="${fn:split(str, ',')}" />
				<c:forEach var="result" items="${strSplit}">
					<c:out value="${result}" /><br>
				</c:forEach>
				<p>권한체크 시 자주사용(해당 메뉴에 대한 접근권한 value에 admin,super등으로 넣고 split해서 if문 사용 해당 권한에 따른 수행권한을 부여) 복수형으로 데이터가 들어 갈 때 나눠줌. 이거 많이 사용한대</p>
			</li>
		</c:when>
		
		<%-- fn:length --%>
		<c:when test="${step eq '14'}">
			<li>
				<h3>fn:length() : 문자열 내부의 문자 수 또는 컬렉션의 항목 수를 반환</h3>
				
				<c:set var="str1" value="This is first string" />
				<c:set var="str" value="1,2,3,4,5,6" />
				<c:set var="strSplit" value="${fn:split(str, ',')}" />
				Length of the String-1(str1) is: <c:out value="${fn:length(str1)}" /><br>
				Length of the String-2(str split) is: <c:out value="${fn:length(strSplit)}" /><br>
				Length of the String-3(str) is: <c:out value="${fn:length(str)}" />
				<p>리스트(배열) 체크 시엔 size</p>
			</li>
		</c:when>
		
		<%-- fn:toLowerCase --%>
		<c:when test="${step eq '15'}">
			<li>
				<h3>fn:toLowerCase() : 문자열의 모든 문자를 소문자로 변환</h3>
				
				<c:set var="string" value="Welcome to JSP Programming" />
				LowerCase : <c:out value="${fn:toLowerCase(string)}" /><br>
				<%-- fn:toUpperCase --%>
				UpperCase : <c:out value="${fn:toUpperCase(string)}" />
			</li>
		</c:when>
		
		<%-- fn:toUpperCase --%>
		<c:when test="${step eq '16'}">
			<li>
				<h3>fn:toUpperCase() : 문자열의 모든 문자를 대문자로 변환</h3>
				
				<c:set var="string" value="Welcome to JSP Programming" />
				<c:out value="${fn:toUpperCase(string)}" />
			</li>
		</c:when>
		
		<%-- fn:substring --%>
		<c:when test="${step eq '17'}">
			<li>
				<h3>fn:substring() : 주어진 시작 및 끝 위치에 따라 문자열의 하위 집합을 반환</h3>
				
				<c:set var="string" value="2022-01-01" />
				0번째부터 4의 앞자리까지 : <c:out value="${fn:substring(string, 0, 4)}" /><br>
				5번째부터 7의 앞자리까지 : <c:out value="${fn:substring(string, 5, 7)}" />
				<p>날짜쪼개기(달력에서 자주사용) / 연도에 대한 유효성 체크시</p>
			</li>
		</c:when>
		
		<%-- fn:replace --%>
		<c:when test="${step eq '18'}">
			<li>
				<h3>fn:replace() : 모든 문자열을 다른 문자열 시퀀스로 바꿈</h3>
				
				<c:set var="string" value="2022-01-01" />
				<c:out value="${fn:replace(string, '-', '.')}" />
				<p>날짜체크 / DB에 날짜를 넣을 땐 -를 빼고, 사용자 화면에 보여 줄 땐 substring을 같이 사용해서 -를 다시 넣어줌</p>
			</li>
		</c:when>
		
		<%-- fn:trim --%>
		<c:when test="${step eq '19'}">
			<li>
				<h3>fn:trim() : 문자열의 양쪽 끝에서 공백을 제거</h3>
				
				<c:set var="str1" value="Welcome to JSP     programming     " />
				<p>String-1 Length is : ${fn:length(str1)}</p>
				
				<c:set var="str2" value="${fn:trim(str1)}" />
				<p>String-2 Length is : ${fn:length(str2)}</p>
				<p>Final value of string is : ${str2}</p>
				<p>주로 자바에서 자주 사용 / html에서 보여주는건 공백을 여러개 쳐도 한칸으로만 보여준다.(표현) 그치만 프로그램은 공백 하나를 한 개로 인식해서 갯수에 포함</p>
			</li>
		</c:when>
		
	<%-- formatting tag --%>
	<%-- fmt:formatDate 사용 시 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>를 맨 위에 추가 --%>
		<c:when test="${step eq '20'}">
			<li>
				<h3>fmt:formatDate : 제공된 패턴 및 스타일을 사용하여 시간 및 날짜를 형식화함 </h3>
				<c:set var="Date" value="<%=new java.util.Date()%>" />
				${Date}<br>
				<fmt:formatDate value="${Date}" pattern="yyyy-MM-dd" />
				<fmt:formatDate value="${Date}" pattern="yyyy.MM.dd" />
				<p>자바에서 받아오는 날짜(문자형)을 날짜형으로 / 게시글 작성일 수정일 등을 가지고 올 때 formatDate를 이용해 pattern의 형식으로 사용자에게 보여줌</p>
			</li>
		</c:when>
		
	</c:choose>
</ul>

</body>
</html>