<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<title>예약관리</title> 

<!-- BBS Style -->
<link href="/asset/BBSTMP_0000000000001/style.css" rel="stylesheet" />
<!-- 공통 Style -->
<link href="/asset/LYTTMP_0000000000000/style.css" rel="stylesheet" />

<script src="https://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 항상 최신버전 사용하기 -->

<!-- jQuery UI -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">

</head>

<body> 

<c:choose>
	<c:when test="${not empty searchVO.resveId}"> <!-- 예약프로그램에 대한 아이디 값이 있으면 -->
		<c:set var="actionUrl" value="/admin/rsv/rsvUpdate.do" /> <!-- 업데이트 -->
	</c:when>
	<c:otherwise>
		<c:set var="actionUrl" value="/admin/rsv/rsvInsert.do" /> <!-- 없으면 입력 -->
	</c:otherwise>
</c:choose>

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
			<form action="${actionUrl}" method="post" id="frm" name="frm" onsubmit="return regist()">
				<input type="hidden" name="resveId" value="${result.resveId}"/> <!-- 히든에 값이 담겨 있어야 된다(update) -->
				
				<table class="chart2">
					<caption>예약정보 작성</caption>
					<colgroup>
						<col style="width:150px" />
						<col />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">프로그램명</th>
							<td>
								<input type="text" id="resveSj" name="resveSj" title="제목입력" class="q3" value="<c:out value="${result.resveSj}"/>"/>
							</td>
						</tr>
						<tr>
							<th scope="row">프로그램 종류</th>
							<td>
								<select id="resveSeCode" name="resveSeCode">
									<option value="TYPE01">선착순</option>
									<option value="TYPE02" <c:if test="${result.resveSeCode eq 'TYPE02'}">selected="selected"</c:if>>승인관리</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">운영기간</th>
							<td> <!-- 스트링으로 받지만 데이터포맷을 사용하기 위해 달력 사용 / readonly 사용 -->
								<input type="text" id="useBeginDt" class="datepicker" name="useBeginDt" title="운영시작일" value="<c:out value="${result.useBeginDt}"/>" readonly="readonly"/>
								~ <input type="text" id="useEndDt" class="datepicker" name="useEndDt" title="운영종료일" value="<c:out value="${result.useEndDt}"/>" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<th scope="row">운영시간</th>
							<td>
								<input type="text" id="useBeginTime" class="timepicker" name="useBeginTime" title="운영시작시간" value="<c:out value="${result.useBeginTime}"/>" readonly="readonly"/>
								~ <input type="text" id="useEndTime" class="timepicker" name="useEndTime" title="운영종료시간" value="<c:out value="${result.useEndTime}"/>" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<th scope="row">신청기간</th>
							<td>
								<input type="text" id="reqstBgnde" class="datepicker" name="reqstBgnde" title="신청시작일" value="<c:out value="${result.reqstBgnde}"/>" readonly="readonly"/>
								~ <input type="text" id="reqstEndde" class="datepicker" name="reqstEndde" title="신청종료일" value="<c:out value="${result.reqstEndde}"/>" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<th scope="row">강사명</th>
							<td>
								<input type="text" id="recNm" name="recNm" title="강사명" value="<c:out value="${result.recNm}"/>"/>
							</td>
						</tr>
						<tr>
							<th scope="row">신청인원수</th>
							<td> <!-- 선착순 시 인원수 체크를 위해 받는다 -->
								<input type="number" id="maxAplyCnt" name="maxAplyCnt" title="신청인원수" value="<c:out value="${result.maxAplyCnt}"/>"/> 명
							</td>
						</tr>
						<tr>
							<th scope="row">내용</th>
							<td>
								<textarea id="resveCn" name="resveCn" title="내용입력" rows="15"><c:out value="${result.resveCn}"/></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				
				<div class="btn-cont ar">
					<c:choose>
						<c:when test="${not empty searchVO.resveId}">
							<c:url var="uptUrl" value="/admin/rsv/rsvRegist.do${_BASE_PARAM}">
								<c:param name="resveId" value="${result.resveId}"/>
							</c:url>
							<a href="${uptUrl}" id="btn-reg" class="btn">수정</a>
							
							<c:url var="delUrl" value="/admin/rsv/rsvDelete.do${_BASE_PARAM}">
								<c:param name="resveId" value="${redult.resveId}"/>
							</c:url>
							<a href="${delUrl}" id="btn-del" class="btn"><i class="ico-del"></i>삭제</a>
						</c:when>
						<c:otherwise>
							<a href="#none" id="btn-reg" class="btn spot">등록</a>
						</c:otherwise>
					</c:choose>
					<c:url var="listUrl" value="/admin/rsv/rsvSelectList.do${_BASE_PARAM}"/>
					<a href="${listUrl}" class="btn">취소</a>
				</div>	
			</form>
		</div>
	</div>
</div>
<!-- //content 끝 -->


<script>
$(document).ready(function(){
	//datepicker 한글로 사용하기 위해 설정
	$(".datepicker").datepicker({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], //디폴트옵션
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], //현재 나오는 요일 명칭 (디폴트). 위 둘 중 수정할 수 있음
		showMonthAfterYear: true, //22년9월 -> 순서 셋팅 
		yearSuffix: '년'
	});
	
	$(".timepicker").timepicker({ //시간 규칙이 있을 때 사용 (분단위 초단위X)
		timeFormat: 'HH:mm',
		interval: 60, //60분 마다 나옴
		minTime: '10', //몇시부터
		maxTime: '18:00', //끝나는시간
		startTime: '10:00', //시작시간
		dropdown: true,
		scrollbar: true
	});
	
	//예약정보 등록
	$("#btn-reg").click(function(){
		$("#frm").submit();
		return false;
	});
	
	//예약 글 삭제
	$("#btn-del").click(function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	});
});

//필수값 체크 (날짜 고를 시 종료시간이 시작시간보다 빠르지 않게 등)
function regist() {
	if(!$("#resveSj").val()){
		alert("프로그램명을 입력해주세요.");
		return false;
	}
}
</script>


</body>
</html>