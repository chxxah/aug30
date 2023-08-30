<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin || CORONA</title>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">


</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		<div class="main">
			<div class="article">			
				<h1>코로나 일일 내역</h1>
				<div>
					<h3>방법 1</h3>
					<table border="1">
						<tr>
							<td>데이터 조회 기준 일시</td>
							<td class="mmddhh">mmddhh</td>
						</tr>
						<tr>
							<td>일일 사망자 수</td>
							<td class="cnt_deaths">cnt_deaths</td>
						</tr>
						<tr>
							<td>일일 	신규 입원자 수</td>
							<td class="cnt_hospitalizations">cnt_hospitalizations</td>
						</tr>
						<tr>
							<td>일일 	확진자 수</td>
							<td class="cnt_confirmations">cnt_confirmations</td> 
						</tr>
					</table>
					
					<h3>방법 2</h3>
					<table border="1">
						<tr>
							<td>데이터 조회 기준 일시</td>
							<td>${result.mmddhh}</td>
						</tr>
						<tr>
							<td>일일 사망자 수</td>
							<td>${result.cnt_deaths}</td>
						</tr>
						<tr>
							<td>일일 	신규 입원자 수</td>
							<td>${cnt_hospitalizations}</td>
						</tr>
						<tr>
							<td>일일 	확진자 수</td>
							<td>${result.cnt_confirmations}</td> 
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	var corona = ${corona};
	document.querySelector(".mmddhh").innerText = corona.response.result[0].mmddhh;
	document.querySelector(".cnt_deaths").innerText = corona.response.result[0].cnt_deaths;
	document.querySelector(".cnt_hospitalizations").innerText = corona.response.result[0].cnt_hospitalizations;
	document.querySelector(".cnt_confirmations").innerText = corona.response.result[0].cnt_confirmations;
</script>
</body>
</html>