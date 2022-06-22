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

<title>게시판</title> 

<!-- BBS Style -->
<link href="/asset/BBSTMP_0000000000001/style.css" rel="stylesheet" />
<!-- 공통 Style -->
<link href="/asset/LYTTMP_0000000000000/style.css" rel="stylesheet" />

<script src="http://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 항상 최신버전 사용하기 -->

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
        selector: '#boardCn', //★ id="boardCn"와 매핑(연결). selector에서 찾아가서 붙여준다 
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


<!-- actionUrl 정의  / 아이디 있을 시 수정 없을 시 등록-->
<c:choose>
	<c:when test="${not empty searchVO.boardId}">
		<c:set var="actionUrl" value="/board/update.do" />
	</c:when>
	<c:otherwise>
		<c:set var="actionUrl" value="/board/insert.do" />
	</c:otherwise>
</c:choose>

<div class="container">
<div id="contents">
	<form action="${actionUrl}" method="post" id="frm" name="frm" onsubmit="return regist()" enctype="multipart/form-data"> <!-- onsubmit 태그에 대한 유효성검사(정상이면 returntrue) 시 사용 (필수값에 사용. 미작성시 경고창 뜨거나 미작성 한 곳으로 커서를 보내는 스크립트 사용 가능) -->
		<input type="hidden" name="boardId" value="${result.boardId}" />
		
		<input type="hidden" name="returnUrl" value="/board/boardRegist.do" /> <!-- 220622 파일 삭제 시 해당 페이지로 다시 오게 한다 -->
		
		<table class="chart2">
			<caption>게시글 작성_이 표는 제목 내용 조회수 날짜로 구성되어있습니다</caption> <!-- 표의 제목과 설명 / 웹접근성을 위해 필수 -->
			<colgroup> <!-- 열 너비 조절 -->
				<col style="width:120px" /> 
				<col /> <!-- 위에서 남은 width값이 자동으로 들어감  / 디바이스 크기에 따라서(반응형웹) -->
			</colgroup>
			
			<tbody> 
			<tr>
				<th scope="row">제목</th> <!-- scope="row" 제목이 세로열로 정의 되어 있다 / 웹접근성 속성 -->
				<td>
					<input type="text" id="boardSj" name="boardSj" title="제목입력" class="q3" value="<c:out value="${result.boardSj}"/>"/>
				</td>
			</tr>
			<tr>
				<th scope="row">공지여부</th>
				<td>
					<label for="noticeAtY">예 : </label> <!-- checkbox는 여러개 선택이라 java에서 array로 받고, radio는 택1 -->
					<input type="radio" id="noticeAtY" value="Y" name="noticeAt" <c:if test="${result.noticeAt eq 'Y'}">checked="checked"</c:if> /> <!-- checked="checked" 체크 되어 있는 상태 -->
					&nbsp;&nbsp;&nbsp; 
					<label for="noticeAtN">아니오 : </label>
					<input type="radio" id="noticeAtN" value="N" name="noticeAt" <c:if test="${result.noticeAt ne 'Y'}">checked="checked"</c:if> /> <!-- result.noticeAt ne 'Y'/ Y가 아닐 때 default값(checked)으로 해주려고 (if문) -->
				</td>
			</tr>
			<tr>
				<th scope="row">비공개여부</th> <!-- 비공개게시판으로 사용 할 경우 hidden처리하거나, 사용자에게 안보이게 한 후 cheked를 '예'에다 세팅 -->
				<td>
					<label for="noticeAtY">예 : </label>
					<input type="radio" id="othbcAtY" value="Y" name="othbcAt" <c:if test="${result.othbcAt eq 'Y'}">checked="checked"</c:if> />
					&nbsp;&nbsp;&nbsp;
					<label for="noticeAtN">아니오 : </label>
					<input type="radio" id="othbcAtN" value="N" name="othbcAt" <c:if test="${result.othbcAt ne 'Y'}">checked="checked"</c:if> />
				</td>
			</tr>
			<tr>
				<th scope="row">작성자ID</th>
				<td>
					<c:out value="${USER_INFO.id}" /> <!-- 작성자ID를 받아만 온다 -->
				</td>
			</tr>
			<tr>
				<th scope="row">내용</th>
				<td> <!-- textarea는 엔터없이 붙여서 사용 / 엔터 후 textaea에 텍스트를 적을시 엔터가 된 상태로 출력된다 -->
					<textarea id="boardCn" name="boardCn" rows="15" title="내용입력"><c:out value="${result.boardCn}" /></textarea> <!-- name은 디비에 들어가는거라 name값을 빼고 넣으면 글이 디비에 들어가지 않는다 / 적절하게 잘 사용하자 -->
				</td>
			</tr>
			
			<!-- 220622 -->
			<c:if test="${not empty result.atchFileId}">
				<tr>
					<th scope="row">기존<br />첨부파일목록</th>
					<td>
						<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
							<c:param name="param_atchFileId" value="${result.atchFileId}" />
						</c:import>
					</td>
				</tr>
			</c:if>
			
			<!-- 220615 파일첨부 -->
			<tr>
				<th scope="row">파일첨부</th>
				<td> <!-- name값을 다르게 해서 넣어주면 여러개 첨부 가능 -->
					<input type="file" name="file_1"><br />
					<input type="file" name="file_2"><br />
					<input type="file" name="file_3"><br />
					<input type="file" name="file_4"><br />
					<input type="file" name="file_5"><br />
				</td>
			</tr>
			</tbody>
		</table>
		
		<div class="btn-cont ar">
			<c:choose>
				<c:when test="${not empty searchVO.boardId}"> <!-- Controller에서 //본인 및 관리자만 허용하게 했기 때문에 -->
					<c:url var="uptUrl" value="/board/update.do">
						<c:param name="boardId" value="${result.boardId}" />
					</c:url>
					<a href="${uptUrl}" id="btn-reg" class="btn">수정</a>
					
					<c:url var="delUrl" value="/board/delete.do">
						<c:param name="boardId" value="${result.boardId}" />
					</c:url>
					<a href="${delUrl}" id="btn-del" class="btn"><i class="ico-del"></i>삭제</a>
				</c:when>
				<c:otherwise>
					<a href="#none" id="btn-reg" class="btn spot">등록</a>
				</c:otherwise>
			</c:choose>
			<c:url var="listUrl" value="/board/selectList.do" />
			<a href="${listUrl}" class="btn">취소</a>
		</div>
	
	</form>
</div>
</div>

<script>
//html에선 위에서 아래로  왼에서 오로 읽으며 만들어짐. 스크립트가 위에 있으면 코드가 만들어지기 전에 스크립트가 이미 실행되서 실제 버튼태그(등록/삭제)을 아래에 구현했으면 버튼 실행이 안돼
$(document).ready(function(){ //$(document).ready 소스가 다 렌더링 한 후 다시 적용해라
	//게시 글 등록
	$("#btn-reg").click(function(){ //뭘 불러오는지 알고 있기! #btn-reg는 id값을 불러옴
		$("#frm").submit();
		return false; //submit을 한 번 하고 끝냄. click에 대한 이벤트를 비활성화. 중복으로 submit 하는걸 방지. 레이어 띄울 때 자주 사용
	});
	
	//게시 글 삭제
	$("#btn-del").click(function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	});
});

function regist(){ //
	if(!$("#boardSj").val()){ //val() -> value값을 가지고 옴 / 제목이 빈칸이면
		alert("제목을 입력해주세요.");
		return false; //종료
	}
//	return; //true 일 땐 생략
	
	//★ 에디터 내용 저장 (에디터에 작성 된 걸 디비에 저장. 태그도 함께 저장) / 에디터 사용 시 꼭 붙여주는데 어떻게 쓰는지  확인!!
	$("#boardCn").val(tinymce.activeEditor.getContent()); //tunymce -> 타이니 기능(변수명) 
}
</script>

</body>
</html>