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
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		<div class="main">
			<div class="article">		
				<div class="div-table">
					<div class="div-row">
						<div class="div-cell">번호</div>
						<div class="div-cell">카테고리</div>
						<div class="div-cell">이름</div>
						<div class="div-cell">url</div>
						<div class="div-cell">참고</div>
					</div>
					<c:forEach items="${multiBoard}" var="row">
					<div class="div-row">
						<div class="div-cell">${row.b_no}</div>
						<div class="div-cell">${row.mb_cate}</div>
						<div class="div-cell">${row.b_catename}</div>
						<div class="div-cell"><a href=".${row.b_url}">보드로 이동</a></div>
						<div class="div-cell">${row.b_comment}</div>
					</div>
					</c:forEach>
				</div>
				
				<div class="input-form">
					<form action="./multiBoard" method="post">
						<input type="number" name="cateNum" required="required" placeholder="게시판 번호 입력">
						<input type="text" name="name" required="required" placeholder="게시판 이름 입력">
						<input type="text" name="comment" required="required" placeholder="참고사항">
						<button type="submit">저장</button>
					</form>
				</div>
				
			</div>
		</div>
	</div>
</body>
</html>