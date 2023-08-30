<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin || 공지사항</title>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="../css/admin.css">
<style type="text/css">
.notice-write-form{
	width: 95%;
	height: auto;
	margin : 10px;
	padding: 20px;
	box-sizing: border-box;
}
.notice-write-form input{
	height: 30px;
	width: 100%;	
}
.notice-write-form textarea {
	width: 100%;
	height: 300px;
	margin: 5px 0px;
}
.notice-write-form button {
	width: 100px;
	height: 50px;
}
table{
	width: 800px;
	text-align: center;
	border-collapse: collapse;
	float: left;
}
tr{
	border-bottom: 1px solid silver;
}
tr:hover{
	background-color: silver;
}
.title{
	text-align: left;
	width: 40%;
	cursor: pointer;
}
.content-view {
	width: calc(100% - 800px);
	height: 400px;
	background-color: gray;
	float: left;
	clear: both;
	display: block;
}

</style>
<script type="text/javascript">
	$(function(){
		$(".title").click(function(){
			//alert($(this).prev().text());
			let nno = $(this).prev().text();
			$.ajax({
				url: "./noticeDetail",
				type: "post",
				dataType: "json",
				data: {nno : nno},
				success: function(data){
					$(".content-view").html(data.content);
				},
				error: function(data){
					alert("오류가 발생했습니다. 다시 시도하지 마세요." + data);					
				}
			});
		});
		
		//보여주기 <-> 감추기 서로 바꿔주기
		$(document).on("click", ".xi-toggle-on, .xi-toggle-off", function(){
			let nno = $(this).parent().siblings(".nno").text();// 글 번호 뽑아내기
			let nnoTD = $(this).parent();// 삭제 버튼의 칸을 선택하기 (삭제 칸 )
			$.ajax({
				url: "./noticeHide",
				type: "post",
				dataType: "json",
				data: {nno : nno},
				success: function(data){
					if(nnoTD.html().indexOf("-off") != -1){
						nnoTD.html('<i class="xi-toggle-on"></i>');
					} else {
						nnoTD.html('<i class="xi-toggle-off"></i>');
					}
				},
				error: function(data){
					alert("오류가 발생했습니다. 다시 시도하지 마세요." + data);					
				}
			});
		});
		
	});
</script>
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		<div class="main">
			<div class="article">			
				<h1>공지사항</h1>
				<!-- 리스트가 없다면 해더도 안 나오게 해주세요  choose문 -->
				<table>
					<tr>
						<td>번호</td>
						<td>제목</td>
						<td>게시일</td>
						<td>글쓴이</td>
						<td>삭제여부</td>
						<td>파일유무</td>
					</tr>
					<c:forEach items="${list}" var="row">
					<tr>
						<td class="nno">${row.nno }</td>
						<td class="title">${row.ntitle }</td>
						<td>${row.ndate }</td>
						<td>${row.m_no }</td>
						<td>
						<c:choose>
							<c:when test="${row.ndel eq 1}"><i class="xi-toggle-on"></i></c:when>
							<c:otherwise><i class="xi-toggle-off"></i></c:otherwise>
						</c:choose>
						</td>
						<td><c:if test="${row.norifile ne null}"><i class="xi-file-add"></i></c:if></td>
					</tr>						
					</c:forEach>
				</table>
				<div class="content-view">
				</div>
				<div class="notice-write-form">
					<form action="./noticeWrite" method="post" enctype="multipart/form-data">
						<input type="text" name="title">
						<textarea name="content"></textarea>
						<input type="file" name="upFile">
						<button type="submit">글쓰기</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>