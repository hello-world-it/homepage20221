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
</head>

<body> 
게시물 총 수 : <c:out value="${paginationInfo.totalRecordCount}" />건
	<table>
		<thead>
			<tr>
				<th>TEMP_ID</th>
				<th>TEMP_VAL</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${resultList}">
				<tr>
					<td><c:out value="${result.tempId}" /></td>
					<td>
						<c:url var="viewUrl" value="/temp2/select.do">
							<c:param name="tempId" value="${result.tempId}" />
						</c:url>
						<a href="${viewUrl}"><c:out value="${result.tempVal}" /></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- 페이징 번호 추가/ value="${pageUrl}을 jsFunction에 넣어준다. 해당 페이지로 이동하라는 뜻.  
	paginationInfo="${paginationInfo}" spring - context-common.xml -->
	<div id="poging_div">
		<ul class="paging_align">
			<c:url var="pageUrl" value="/temp2/selectList.do?" /> <!-- jstl -->
			<c:set var="pagingParam"><c:out value="${pageUrl}" /></c:set>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="${pagingParam}"/>
		</ul>
	</div>
	
	<!-- 글쓰기 버튼은 List에서 노출 -->
	<a href="/temp2/tempRegist.do">등록하기</a> 
	
</body>
</html>