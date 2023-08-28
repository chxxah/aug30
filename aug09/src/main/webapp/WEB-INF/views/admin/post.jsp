<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin || multiBoard</title>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">
<link rel="stylesheet" href="../css/multiboard.css">
<script src="../js/jquery-3.7.0.min.js"></script>
<style type="text/css">
	 .boardlist {
        margin: 20px auto; /* 수평 가운데 정렬을 위해 margin을 auto로 설정 */
        text-align: center; /* 내부 요소들을 가운데 정렬 */
    }

    .gray {
        background-color: gray;
    }

    form {
        float: right;
    }
    
    .mb_detail {
    	clear: both;
    	overflow: hidden;
    	display: flex;
	}
	
    .mb_content {
	    /* display: flex; */
	    display: block;
    	white-space: pre-line;
    	word-wrap: break-word;
	}
	
	
	

</style>
<script type="text/javascript">
	$(function(){
		$(".title").click(function(){
			var mbno = $(this).siblings(".mb_no").text();
            var mbdetail = $(this).parent().siblings("." + mbno);
            var mb_content = $(this).parent().siblings(".mb_detail").children();
            if (mb_content.is(":visible")) {
            	$.ajax({
					url: "./detail",
					type: "get",
					data: {mbno:mbno},
					dataType: "json",
					success: function (data) {
						mbdetail.remove();
					},
					error: function (error) {
						alert("에러발생");
					}
				});
            } else {
				$.ajax({
					url: "./detail",
					type: "get",
					data: {mbno:mbno},
					dataType: "json",
					success: function (data) {
							mbdetail.html('<div class="mb_content">' + data.content + '</div>');
					},
					error: function (error) {
						alert("에러발생");
					}
				});
            	
            	
            }
            
            
		});
	});
</script>
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		<div class="main">
			<div class="article">	
			<h1>게시글 관리 ${list[0].count}개의 글이 있음</h1>	
				<div class="boardlist">
		            <button onclick="location.href='./post?cate=0'">전체보기</button>
					<c:forEach items="${boardlist}" var="b">
		            		<button onclick="location.href='./post?cate=${b.mb_cate}'">${b.b_catename }</button>
	            	</c:forEach>
	            	<form action="./post" method="get">
	            		<select name="searchN">
	            			<option value="title">제목</option>
	            			<option value="content">내용</option>
	            			<option value="nick">작성자</option>
	            			<option value="id">ID</option>
	            		</select>
	            		<input type="text" name="searchV" required="required">
	            		<input type="hidden" name="cate" value="${param.cate}">
	            		<button type="submit">검색</button>
	            	</form>
            	</div>
				<div class="div-table">
					<div class="div-row">
						<div class="div-cell">번호</div>
						<div class="div-cell">카테고리</div>
						<div class="div-cell">제목</div>
						<div class="div-cell">작성자</div>
						<div class="div-cell">날짜</div>
						<div class="div-cell">조회수</div>
						<div class="div-cell">삭제여부</div>
					</div>
					<c:forEach items="${list}" var="row">
					<div class="div-row <c:if test="${row.mb_del eq 0}">gray</c:if> mb_detail-container">
						<div class="div-cell mb_no" style="flex-basis: 10px;">${row.mb_no}</div> 
						<div class="div-cell">${row.b_catename}</div> 
						<div class="div-cell title">${row.mb_title}</div>
						<div class="div-cell">${row.m_name}(${row.m_id})</div>
						<div class="div-cell">${row.mb_date}</div>
						<div class="div-cell">${row.mb_read}</div>
						<div class="div-cell">${row.mb_del}</div>
					</div>
					<div class="${row.mb_no } mb_detail"></div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>