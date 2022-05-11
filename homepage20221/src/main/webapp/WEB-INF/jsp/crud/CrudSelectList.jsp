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
<title>데이터 가져오기~_List가져오기 / 상세페이지와 등록페이지</title> 
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 항상 최신버전 사용하기 -->

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
게시물 총 수 : <c:out value="${paginationInfo.totalRecordCount}" />건
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th> 
				<th>관리</th> 
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${resultList}">
				<tr>
					<td><c:out value="${result.crudId}" /></td>	
								
					<td>
						<c:url var="viewUrl" value="/crud/select.do">
							<c:param name="crudId" value="${result.crudId}" />
						</c:url>
						<a href="${viewUrl}"><c:out value="${result.crudSj}" /></a>
					</td>

					<td>
						<c:out value="${result.userNm}" />
					</td>
					
					<td>
						<c:out value="${result.frstRegistPnttm}" /> 
					</td>
					<!-- DATE : SQL에 정의 해 놓으면 그대로 가지고 온다 -->
					
					<td>
						<c:url var="delUrl" value="/crud/delete.do">
						<c:param name="crudId" value="${result.crudId}" />
						</c:url>
						<a href="${delUrl}" class="btn-del">삭제</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 페이징 번호 추가/ value="${pageUrl}을 jsFunction에 넣어준다. 해당 페이지로 이동하라는 뜻.  
	paginationInfo="${paginationInfo}" spring - context-common.xml -->
	<div id="poging_div">
		<ul class="paging_align">
			<c:url var="pageUrl" value="/crud/selectList.do?" /> <!-- jstl -->
			<c:set var="pagingParam"><c:out value="${pageUrl}" /></c:set>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="${pagingParam}"/>
		</ul>
	</div>
	
	<!-- 글쓰기 버튼은 List에서 노출 -->
	<a href="/crud/crudRegist.do">글쓰기</a> 
	
</body>
</html>