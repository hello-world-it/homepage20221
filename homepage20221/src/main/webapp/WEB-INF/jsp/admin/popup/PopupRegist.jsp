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

<title>팝업관리</title> 

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
	<c:when test="${not empty searchVO.popupId}"> 
		<c:set var="actionUrl" value="/admin/popup/update.do" />
	</c:when>
	<c:otherwise>
		<c:set var="actionUrl" value="/admin/popup/insert.do" />
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
				<input type="hidden" name="popupId" value="${result.popupId}"/>
				
				<table class="chart2">
					<caption>팝업정보 작성</caption>
					<colgroup>
						<col style="width:200px" />
						<col />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">팝업명</th>
							<td>
								<input type="text" id="popupTitleNm" name="popupTitleNm" title="제목입력" class="q3" value="<c:out value="${result.popupTitleNm}"/>"/>
							</td>
						</tr>
						<tr>
							<th scope="row">팝업유형</th>
							<td>
								<select id="sysTyCode" name="sysTyCode">
									<option value="TYPE1">일반팝업</option>
									<option value="TYPE2" <c:if test="${result.sysTyCode eq 'TYPE2'}">selected="selected"</c:if>>레이어팝업</option>
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row">게시기간</th>
							<td>
								<input type="text" id="ntceBgnde" class="datepicker" name="ntceBgnde" title="게시시작일" value="<c:out value="${result.ntceBgnde}"/>" readonly="readonly"/>
								~ <input type="text" id="ntceEndde" class="datepicker" name="ntceEndde" title="게시종료일" value="<c:out value="${result.ntceEndde}"/>" readonly="readonly"/>
							</td>
						</tr>
						<tr class="type1">
							<th scope="row">팝업창위치(가로)</th>
							<td>
								<input type="number" id="popupWlc" name="popupWlc" title="팝업창위치(가로)" value="<c:out value="${result.popupWlc}"/>"/>PX
							</td>
						</tr>
						<tr class="type1">
							<th scope="row">팝업창위치(세로)</th>
							<td>
								<input type="number" id="popupHlc" name="popupHlc" title="팝업창위치(세로)" value="<c:out value="${result.popupHlc}"/>"/>PX
							</td>
						</tr>
						<tr class="type1">
							<th scope="row">팝업창사이즈(가로)</th>
							<td>
								<input type="number" id="popupWsize" name="popupWsize" title="팝업창사이즈(가로)" value="<c:out value="${result.popupWsize}"/>"/>PX
							</td>
						</tr>
						<tr class="type1">
							<th scope="row">팝업창사이즈(세로)</th>
							<td>
								<input type="number" id="popupHsize" name="popupHsize" title="팝업창사이즈(세로)" value="<c:out value="${result.popupHsize}"/>"/>PX
							</td>
						</tr>
						<tr>
							<th scope="row">내용</th>
							<td>
								<textarea id="popupCn" name="popupCn" title="내용입력" rows="15"><c:out value="${result.popupCn}"/></textarea>
							</td>
						</tr>
					</tbody>
				</table>
				
				<div class="btn-cont ar">
					<c:choose>
						<c:when test="${not empty searchVO.popupId}">
							<c:url var="uptUrl" value="/admin/popup/update.do${_BASE_PARAM}">
								<c:param name="popupId" value="${result.popupId}"/>
							</c:url>
							<a href="${uptUrl}" id="btn-reg" class="btn">수정</a>
							
							<c:url var="delUrl" value="/admin/popup/delete.do${_BASE_PARAM}">
								<c:param name="popupId" value="${result.popupId}"/>
							</c:url>
							<a href="${delUrl}" id="btn-del" class="btn"><i class="ico-del"></i>삭제</a>
						</c:when>
						<c:otherwise>
							<a href="#none" id="btn-reg" class="btn spot">등록</a>
						</c:otherwise>
					</c:choose>
					<c:url var="listUrl" value="/admin/popup/selectList.do${_BASE_PARAM}"/>
					<a href="${listUrl}" class="btn">취소</a>
				</div>	
			</form>
		</div>
	</div>
</div>
<!-- //content 끝 -->

<script src="https://cdn.tiny.cloud/1/f4jgwu1ymxy0ednvwrd1af0ozo4kbyfl3r1kc3n0rkap3ak1/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
<script>
$(function(){
    var plugins = [ //★ 기능 추가시  ex 유료버전 사용 시 추가. 
        "advlist", "autolink", "lists", "link", "image", "charmap", "print", "preview", "anchor",
        "searchreplace", "visualblocks", "code", "fullscreen", "insertdatetime", "media", "table",
        "paste", "code", "help", "wordcount", "save"
    ];
    var edit_toolbar = 'formatselect fontselect fontsizeselect |' //★ 기능 추가시
               + ' forecolor backcolor |'
               + ' bold italic underline strikethrough |'
               + ' alignjustify alignleft aligncenter alignright |'
               + ' bullist numlist |'
               + ' table tabledelete |'
               + ' link image';

    tinymce.init({
    	language: "ko_KR", // 한글판으로 변경
        selector: '#popupCn', //★ id="popupCn"와 매핑(연결). selector에서 찾아가서 붙여준다 
        height: 500,
        menubar: false,
        plugins: plugins,
        toolbar: edit_toolbar,
        
        /*** image upload ***/
        image_title: true,
        /* enable automatic uploads of images represented by blob or data URIs*/
        automatic_uploads: true,
        /*
            URL of our upload handler (for more details check: https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_url)
            images_upload_url: 'postAcceptor.php',
            here we add custom filepicker only to Image dialog
        */
        file_picker_types: 'image',
        /* and here's our custom image picker*/
        file_picker_callback: function (cb, value, meta) {
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');

            /*
            Note: In modern browsers input[type="file"] is functional without
            even adding it to the DOM, but that might not be the case in some older
            or quirky browsers like IE, so you might want to add it to the DOM
            just in case, and visually hide it. And do not forget do remove it
            once you do not need it anymore.
            */
            input.onchange = function () {
                var file = this.files[0];

                var reader = new FileReader();
                reader.onload = function () {
                    /*
                    Note: Now we need to register the blob in TinyMCEs image blob
                    registry. In the next release this part hopefully won't be
                    necessary, as we are looking to handle it internally.
                    */
                    var id = 'blobid' + (new Date()).getTime();
                    var blobCache =  tinymce.activeEditor.editorUpload.blobCache;
                    var base64 = reader.result.split(',')[1];
                    var blobInfo = blobCache.create(id, file, base64);
                    blobCache.add(blobInfo);

                    /* call the callback and populate the Title field with the file name */
                    cb(blobInfo.blobUri(), { title: file.name });
                };
                reader.readAsDataURL(file);
            };
            input.click();
        },
        /*** image upload ***/
        
        content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
    });
});
</script>

<script>
$(document).ready(function(){
	//datepicker 한글로 사용하기 위해 설정
	$(".datepicker").datepicker({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달', // 이전 버튼 title
		nextText: '다음 달', // 다음 버튼 title
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'], //디폴트옵션
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'], // 요일 표시 //현재 나오는 요일 명칭 (디폴트). 위 둘 중 수정할 수 있음
		showMonthAfterYear: true, // 연도 다음에 월 표시 여부 //22년9월 -> 순서 셋팅 
		yearSuffix: '년' // 연도 숫자 다음에 나올 글자
	});
	
	//등록
	$("#btn-reg").click(function(){
		$("#frm").submit();
		return false;
	});
	
	//삭제
	$("#btn-del").click(function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	});
	
	//팝업유형
	$("#sysTyCode").change(function(){
		var sysTyCode = $(this).val();
		
		if(sysTyCode == "TYPE1") {
			$(".type1").show();
		}else {
			$(".type1").hide();
		}
	});
});

//필수값 체크
function regist() {
	if(!$("#popupTitleNm").val()){
		alert("팝업명을 입력해주세요.");
		return false;
	}
}
</script>

</body>
</html>