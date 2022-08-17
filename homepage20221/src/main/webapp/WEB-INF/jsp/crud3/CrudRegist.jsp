<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko">
<title>데이터 가져오기~</title> 

<style>
table {
	border-collapse: collapse;
	border: 1px solid;
	
}
th, td {
	border: 1px solid;
	padding: 5px;
}
</style>

</head>

<body>
	<!-- method : select, update, delete도 있다? / 속도는 get이 더 빨라. 보안 / 개발시 get으로 url확인(유지보수) / 
		게시판은 post를 주로 사용(1.edit기능을 사용하기 때문에 get을 쓰면 파라미터 길이제한, 2.첨부파일은 get으로 보낼 수 없어) /
		action=url: 주소로 내용을 전송 / name값이 tempVO의 값과 일치해야 값이 담김  -->
	<!-- * 등록폼
	<form action="/temp/insert.do" method="post" name="tempVO"> <!-- ValueObject 
		<label for="tempVal">값 정보 : </label> <!-- input에 대한 이름 for과 id 맞춰줘  - 웹표준 / 웹에서 라벨을 클릭하면 인풋박스에 커서가 가게
		<input type="text" id="tempVal" name="tempVal" value="" />
		<br />
		<button type="submit">등록</button>
	</form>	 -->
	
<!-- <table>
	<tbody>
		<tr>
			<th>제목</th>
			<td></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td></td>
		</tr>
		<tr>
			<th>내용</th>
			<td></td>
		</tr>
	</tbody>
</table> -->
	
	<!-- tempId를 체크 하고 actionUrl 정의 / id가 있으면 update 없으면 insert -->
	<c:choose>
		<c:when test="${not empty searchVO.crudId}">
			<c:set var="actionUrl" value="/crud3/update.do" />
		</c:when> 
		<c:otherwise>
			<c:set var="actionUrl" value="/crud3/insert.do"/>
		</c:otherwise>
	</c:choose>
	
	<!-- hidden으로 id를 보내서  -->
	<form action="${actionUrl}" method="post" name="crud3VO">
		<input type="hidden" name="crudId" value="${result.crudId}" />
		<label for="crudSj">제목: </label>
		<input type="text" id="crudSj" name="crudSj" value="${result.crudSj}"/>
		<br />
		
		<label for="userNm">작성자: </label>
		<input type="text" id="userNm" name="userNm" value="${result.userNm}"/>
		<br />
		
		<label for="crudCn">내용: </label>
		<textarea row="10" name="crudCn"><c:out value="${result.crudCn}"/></textarea> <!-- textarea는 value속성이 없으니 c:out으로 -->
		<%-- <input type="text" id="crudCn" name="crudCn" value="${result.crudCn}"/> --%>
		<br />
		
	<!-- id가 있으면 수정 / 없으면 등록 -->
		<c:choose>
			<c:when test="${not empty searchVO.crudId}">
			 	<button type="submit">수정</button>
			</c:when>
			<c:otherwise>
				<button type="submit">등록</button>
			</c:otherwise>
		</c:choose>
		<a href="/crud3/selectList.do">취소</a>
	</form>
	
</body>
</html>