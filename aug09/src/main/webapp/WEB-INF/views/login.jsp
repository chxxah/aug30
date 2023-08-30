<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> ❤ login</title>
    <link href="css/styles.css" rel="stylesheet" />
    <script src="./js/jquery-3.7.0.min.js"></script>
	<style type="text/css">
		.login-form{
			margin: 0 auto;
			padding: 10px;
			width: 400px;
			height: auto;
			background-color: rgba(240, 255, 240, 0.3);
		}
	</style>
	<script>
	//자바스크립트일때
		function loginCheck() {
			let id = document.querySelector("#id");//창입니다
			let pw = document.querySelector("#pw");//input창 입니다
			let checkItems = [ id, pw ];
			//alert(id + " / " + pw + " / " + checkItems);
			
			
			
			
			
			let flag = checkItems.every(function(item) {
				if (item.value === null || item.value === "") {
					//alert(item.parentNode.parentNode.childNodes[1].innerHTML + "를 다시 입력해주세요");
					//alert(item.parentNode.parentNode.querySelector("label").innerHTML + "를 다시 입력해주세요");
					alert(item.parentNode.previousElementSibling.innerHTML + "를 다시 입력해주세요");
					item.focus();
				}
				return item.value !== "";//비어있으면 거짓,
			});

			if (flag == true) {
				//alert("로그인합니다");
				//가상 form만들기
				let form = document.createElement("form");
				form.setAttribute("action", "./login.sik");
				form.setAttribute("method", "post");
				
				let idField = document.createElement("input");
				idField.setAttribute("type", "hidden");
				idField.setAttribute("name", "id");
				idField.setAttribute("value", id.value);
				form.appendChild(idField);
				
				let pwField = document.createElement("input");
				pwField.setAttribute("type", "hidden");
				pwField.setAttribute("name", "pw");
				pwField.setAttribute("value", pw.value);
				form.appendChild(pwField);
				
				document.body.appendChild(form);
				form.submit();				
			}
		}
	</script>
	
	<script type="text/javascript">
		$(function(){
				alert("!");
				// 쿠키 값 가져오기
				let userID = getCookie("userID");// 아이디
				let setCookieY = getCookie("setCookie");// Y
				
				// 쿠키 검사 -> 쿠키가 있다면 해당 쿠키에서 id값 가져와서 id 칸에 붙여넣기
				if(setCookieY == 'Y'){
					$("#saveID").prop("checked", true); 
					$("#id").val(userID);
					$("#pw").focus();
				} else {
					$("#saveID").prop("checked", false);
				}
				
				// (쿠키가 있다면 = 아이디를 저장한 적이 있다면)
		
				
			$("#login").click(function(){
				// 아이디 패스워드 검사하기
				let id = $("#id").val();// 사용자가 입력한 아이디 값
				let pw = $("#pw").val();// 사용자가 입력한 패스워드 값
				
				if(id == '' || id.length < 3){
					alert("올바른 아이디를 입력해주세요.");
					$("#id").focus();
					return false;
				}
				
				if(pw == '' || pw.length < 3){
					alert("올바른 암호를 입력해 주세요.");
					$("#pw").focus();
					return false;
				}
				
				if($("#saveID").is(":checked")){//네모칸이 체크되어있다면
					alert("체크되어 있습니다.");
					setCookie("userID", id, 7);// 호출해서 사용
					setCookie("setCookie", "Y", 7);
					//
				} else {
					//alert("x");
					//deleteCookie() 함수
					deleteCookie("userID");
					deleteCookie("setCookie");
				}
				let form = document.createElement("form");
				form.setAttribute("action", "./login.sik");
				form.setAttribute("method", "post");
				
				let idField = document.createElement("input");
				idField.setAttribute("type", "hidden");
				idField.setAttribute("name", "id");
				idField.setAttribute("value", id);
				form.appendChild(idField);
				
				let pwField = document.createElement("input");
				pwField.setAttribute("type", "hidden");
				pwField.setAttribute("name", "pw");
				pwField.setAttribute("value", pw);
				form.appendChild(pwField);
				
				document.body.appendChild(form);
				form.submit();	
			});
		});
		
		// setCookie("변수명", 사용자가 입력한 ID, 유기간)
		function setCookie(cookieName, value, exdays){
			let exdate = new Date(); // 날짜 뽑아오기
			exdate.setDate(exdate.getDate() + exdays);
											//exdays가 널이라면 비워놓고 아니라면 뒤에를 함 (삼항연산자)
			let cookieValue = value + ((exdays == null) ? "" : ";expires=" + exdate.toGMTString());
			document.cookie = cookieName + "=" + cookieValue;
			//                userID=peazh; expires=2023-08-30
		}
		
		
		// deleteCookie()
		function deleteCookie(cookieName){
			let expireDate = new Date();
			expireDate.setDate(expireDate.getDate() -1);// 만료날짜를 어제로 바꿔줌
			document.cookie = cookieName+"= "+";expires="+expireDate.toGMTString();
		}
		
		// getCookie()
		function getCookie(cookieName){
			let x, y;
			let val = document.cookie.split(";");
			for(let i = 0; i < val.length; i++){
				x = val[i].substr(0, val[i].indexOf("="));
				y = val[i].substr(val[i].indexOf("=") + 1);
				//x = x.replace(/^\s+|\s+$/g, '');// 공백 제거하는 역할
				x = x.trim();
				if(x == cookieName){
					return y;
				}
			}
		}
		
		
	</script>
</head>
<body>
<%@ include file="menu.jsp" %>
        <header class="masthead">
            <div class="container">
               <div class="rounded-3 login-form">
               		<h2>LOGIN</h2>
               		<img alt="login" src="./img/login.png" width="250px;">
               	<c:if test="${param.error eq 'login' }">	
               	<div class="mb-3 row">
               		<h2 style="color:red;">로그인 하세요.</h2>	
               	</div>
               	</c:if>
				<div class="mb-3 row">
					<label for="staticEmail" class="col-sm-3 col-form-label">I D</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="id" placeholder="아이디를 입력하세요">
					</div>
				</div>
				<div class="mb-3 row">
					<label for="inputPassword" class="col-sm-3 col-form-label">Password</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" id="pw" placeholder="암호를 입력하세요">
					</div>
				</div>
				<div class="mb-3 row">
					<div class="col-sm-12">
						<input type="checkbox" class="saveID" id="saveID">
						<label for="saveID">아이디 저장</label>
					</div>
				</div>
				<div class="mb-3 row">
					<div class="col-sm-12">
						<input type="button" id="login" class="btn btn-primary" value="login"><!--  onclick="loginCheck() -->
						<input type="button" id="join" class="btn btn-info" value="가입하기">
					</div>
				</div>
               </div>

            </div>
        </header>
        
<!-- 에러가 들어오면 동작하게 하겠습니다 -->
<%-- <c:if test="${param.error ne null }">
	<script type="text/javascript">
		alert("로그인 해야 사용할 수 있는 메뉴입니다.");
	</script>
</c:if>      --%>
        

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>