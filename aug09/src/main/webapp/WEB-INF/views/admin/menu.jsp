<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div class="menu">
			<div class="menu-logo" onclick="url('main')"><img alt="" src="../img/kakao.png"></div>
			<div class="menu-item" onclick="url('multiBoard')"><i class="xi-layout xi-2x"></i>게시판 관리</div>
			<div class="menu-item" onclick="url('post?cate=0')"><i class="xi-paper-o xi-2x"></i>게시글 관리</div>
			<div class="menu-item" onclick="url('member')"><i class="xi-user-o xi-2x"></i>회원 관리</div>
			<div class="menu-item" onclick="url('air')"><i class="xi-comment-o xi-2x"></i>공기질 관리</div>
        	<div class="menu-item" onclick="url('corona')"><i class="xi-message-o xi-2x"></i>코로나</div>
			<div class="menu-item" onclick="url('mail')"><i class="xi-document xi-2x"></i>메일 보내기</div>
			<div class="menu-item" onclick="url('notice')"><i class="xi-bell-o xi-2x"></i>공지사항</div>
			<div class="menu-item" onclick="url('logout')"><i class="xi-flag-o xi-2x"></i>로그아웃</div>
		</div>
		<script>function url(url){location.href="./"+url;}</script>