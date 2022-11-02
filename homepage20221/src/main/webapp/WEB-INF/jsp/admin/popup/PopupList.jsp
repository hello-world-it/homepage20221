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

<title>팝업리스트</title> 

<!-- BBS Style -->
<link href="/asset/BBSTMP_0000000000001/style.css" rel="stylesheet" />
<!-- 공통 Style -->
<link href="/asset/LYTTMP_0000000000000/style.css" rel="stylesheet" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 항상 최신버전 사용하기 -->
</head>

<body> 

<%-- 기본URL --%>
<c:url var="_BASE_PARAM" value="">
	<c:param name="searchCondition" value="${searchVO.searchCondition}" />
	<c:if test="${not empty searchVO.searchKeyword}">
		<c:param name="searchKeyword" value="${searchVO.searchKeyword}" />
	</c:if>
</c:url>

<!-- content 시작 -->
<div id="content">
	<div class="container">
		<div id="contents">
		
		<%-- 검색영역 --%>
		<div id="bbs_search">
			<form name="frm" method="post" action="/admin/popup/selectList.do">
				<fieldset>
					<legend>검색조건입력폼</legend>
					<label for="ftext" class="hdn">검색분류선택</label>
					<select name="searchCondition" id="ftext">
						<option value="0" <c:if test="${searchVO.searchCondition eq '0'}">selected="selected"</c:if>>팝업창</option>
						<option value="1" <c:if test="${searchVO.searchCondition eq '1'}">selected="selected"</c:if>>내용</option>
					</select>
					<label for="inp_text" class="hdn">검색어입력</label>
					<input name="searchKeyword" value="<c:out value="${searchVO.searchKeyword}"/>" type="text" class="inp_s" id="inp_text"/>
					<span class="bbtn_s"><input type="submit" value="검색" title="검색(팝업관리 게시물 내)"/></span>
				</fieldset>
			</form>
		</div>
		
		<%-- 목록영역 --%>
		<div id="bbs_wrap">
			<div class="total">
				총 게시물 <strong><c:out value="${paginationInfo.totalRecordCount}"/></strong>건 | 현재페이지 <strong><c:out value="${paginationInfo.currentPageNo}"/></strong>/<c:out value="${paginationInfo.totalPageCount}"/>
			</div>
			
			<div class="bbs_list">
				<table class="list_table">
					<thead>
						<tr>
							<th class="num" scople="col">번호</th>
							<th class="tit" scople="col">팝업명</th>
							<th scope="col">팝업유형</th>
							<th scope="col">팝업기간</th>
							<th scope="col">미리보기</th>
							<th scope="col">관리</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${resultList}" varStatus="status">
							<tr>
								<td class="num"><c:out value="${paginationInfo.totalRecordCount - ((searchVO.pageIndex-1) * searchVO.pageUnit) - (status.count - 1)}"/></td>
								<td class="tit"><c:out value="${result.popupTitleNm}"/></td>
								<td>
									<c:choose>
										<c:when test="${result.sysTyCode eq 'TYPE1'}">일반팝업</c:when>
										<c:when test="${result.sysTyCode eq 'TYPE2'}">레이어팝업</c:when>
										<c:otherwise>-</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:out value="${result.ntceBgnde}"/>~<br/>
									<c:out value="${result.ntceEndde}"/>
								</td>
								<td>
									<c:url var="previewUrl" value="/popup/select.do">
										<c:param name="popupId" value="${result.popupId}"/>
									</c:url>
									<a href="${previewUrl}" class="btn spot btn-prev" data-title="${result.popupTitleNm}" data-hlc="${result.popupHlc}" data-wlc="${result.popupWlc}" data-hsize="${result.popupHsize}" data-wsize="${result.popupWsize}">미리보기</a>
								</td>
								<td>
									<c:url var="updateUrl" value="/admin/popup/regist.do${_BASE_PARAM}">
										<c:param name="popupId" value="${result.popupId}"/>
										<c:param name="pageIndex" value="${searchVO.pageIndex}"/>
									</c:url>
									<a href="${updateUrl}" class="btn spot">수정</a>
									<br/><br/>

									<c:url var="deleteUrl" value="/admin/popup/delete.do${_BASE_PARAM}">
										<c:param name="popupId" value="${result.popupId}"/>
										<c:param name="pageIndex" value="${searchVO.pageIndex}"/>
									</c:url>
									<a href="${deleteUrl}" class="btn spot btn-del">삭제</a>
								</td>
							</tr>
						</c:forEach>
							
						<%-- 글이 없을 경우 --%>
						<c:if test="${fn:length(resultList) == 0}">
							<tr class="empty">
								<td colspan="6">검색 데이터가 없습니다.</td>
							</tr>
						</c:if>
					
					</tbody>
				</table>
			</div>
			
			<div id="paging">
				<c:url var="pageUrl" value="/admin/popup/selectList.do${_BASE_PARAM}"/>
				<c:set var="pagingParam"><c:out value="${pageUrl}"/></c:set>
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="${pagingParam}"/>
			</div>
		</div>
			
		<div class="btn-cont ar">
			<a href="/admin/popup/regist.do" class="btn spot"><i class="ico-check-spot"></i> 등록</a>
		</div> 
			
		</div>
	</div>
</div>	
<!-- //content 끝 -->

<script>
<c:if test="${not empty message}">
	alert("${message}");
</c:if>

//미리보기
$(".btn-prev").click(function(){
	var href= $(this).attr("href"),
		title = $(this).data("title"),
		hlc = $(this).data("hlc"),
		wlc = $(this).data("wlc"),
		hsize = $(this).data("hsize"),
		wsize = $(this).data("wsize");
	
	//팝업오픈
	window.open(href, title, "top=" + hlc + ", left=" + wlc + ", width=" + wsize + ", height=" + hsize);
	
	return false;
});

//팝업 삭제
$(".btn-del").click(function(){
	if(!confirm("삭제하시겠습니까?")){
		return false;
	}
});
</script>
		
</body>
</html>