<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 가져오기~_상세페이지 가져오기</title>

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

<!-- 최신버전의 스크립트를 가져오는 url -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>

<body> 

	<table>
	<tbody>
		<tr>
			<th>제목</th>
			<td><c:out value="${result.crud2Sj}" /></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><c:out value="${result.user2Nm}" /></td>
		</tr>
		<tr>
			<th>작성일</th>
			<!-- <td><c:out value="${result.frst2RegistPnttm}" /></td> -->
			<td><fmt:formatDate value="${result.frst2RegistPnttm}" pattern="yyyy-MM-dd"/></td> <!-- 포멧데이터를 사용해서 날짜 출력 -->
		</tr>
		<tr>
			<th>내용</th>
			<td><c:out value="${result.crud2Cn}" /></td>
		</tr>
	</tbody>
	</table>
	
	<!--  -->
	<div class="box-btn">
		<c:url var="uptUrl" value="/crud2/crudRegist.do">
			<c:param name="crud2Id" value="${result.crud2Id}" />
		</c:url>
		<a href="${uptUrl}">수정</a>
		
		<c:url var="delUrl" value="/crud2/delete.do">
			<c:param name="crud2Id" value="${result.crud2Id}" />
		</c:url>
		<a href="${delUrl}" class="btn-del">삭제</a>
		
		<a href="/crud2/selectList.do">목록</a>
	</div>
	
	<script>
	$(document).ready(function(){
		$(".btn-del").click(function(){
			if(!confirm("삭제하시겠습니까?")){ 
				return false; 
			}
		});
	});
	</script>
	
</body>
</html>