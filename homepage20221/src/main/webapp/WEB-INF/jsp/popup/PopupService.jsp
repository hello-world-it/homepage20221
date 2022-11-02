<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<script src="/asset/cookie.js"></script>

<script>
$(document).ready(function(){
	<c:forEach var="result" items="${resultList}">
	<c:choose>
		<%-- 일반팝업 --%>
		<c:when test="${result.sysTyCode eq 'TYPE1'}">
			if($.cookie("${result.popupId}") != "done") {
				window.open("/popup/select.do?popupId=${result.popupId}","${result.popupTitleNm}","top=${result.popupHlc}, left=${result.popupWlc}, width=${result.popupWsize}, height=${result.popupHsize}");
			}
		</c:when>
		<%-- 일반팝업 --%>
		<c:otherwise>
			var html = `
					<div id="layer-${result.popupId}" class="layer-popup" style="display:block;">
						<header class="layer-header">
							<c:out value="${result.popupTitleNm}"/>
							<button type="button" class="layer-close" data-id="${result.popupId}"><span>팝업닫기</span></button>
						</header>
						<div class="layer-body">
							<c:out value="${result.popupCn}" escapeXml="false"/>
						</div>
					</div>
				`;
			$("body").append(html);
		</c:otherwise>
	</c:choose>
</c:forEach>

//레이어팝업닫기
$(".layer-close").click(function(){
	var id = $(this).data("id");
	$("#layer-" + id).hide();
});

});
</script>