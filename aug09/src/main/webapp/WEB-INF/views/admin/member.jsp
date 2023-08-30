<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin || memberlist</title>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css">
<link rel="stylesheet" href="../css/multiboard.css">
<style type="text/css">
	.gray {
		background-color: gray;
	}
	.red {
		color: red;
	}
</style>
<script type="text/javascript">
function gradeCh(mno, name, value){
	if(confirm(name + "님의 등급을 변경하시겠습니까?")){
		location.href="./gradeChange?mno="+mno+"&grade="+value;
	}
	/* alert(value); // 1
	alert( mno + "번의 " + name + "님을 변경하시겠습니까?" );// 1번의 한여름님을 변경하시겠습니까?
	let select = document.getElementById("grade");
	let selectName = select.options[select.selectedIndex].text;
	let selectValue = select.options[select.selectedIndex].value;
	alert(selectName + " : " + selectValue);// 탈퇴 회원 : 1 */
	
	
}

</script>

<!-- <script type="text/javascript">

   function gradeCh(mno, name) {
     alert(mno+name);
     let selectElement = document.getElementById(mno); 
     let selectedValue = selectElement.value;
     let selectedName = selectElement.options[selectElement.selectedIndex].text;
     alert(selectedValue + selectedName);
   }
</script> -->
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		<div class="main">
			<div class="article">	
				<h1>사용자 보기</h1>	
				<div class="div-table">
					<div class="div-row table-head">
						<div class="div-cell table-head">번호</div>
						<div class="div-cell table-head">아이디</div>
						<div class="div-cell table-head">이름</div>
						<div class="div-cell table-head">가입일</div>
						<div class="div-cell table-head">주소</div>
						<div class="div-cell table-head">생년월일</div>
						<div class="div-cell table-head">mbti</div>
						<div class="div-cell table-head">성별</div>
						<div class="div-cell table-head">등급</div>
						
					</div>
					<c:forEach items="${memberList}" var="row">
					<div class="div-row <c:if test="${row.m_grade lt 2 }">gray</c:if>
					<c:if test="${row.m_grade gt 6 }">red</c:if>" >
						<div class="div-cell">${row.m_no}</div>
						<div class="div-cell">${row.m_id}</div>
						<div class="div-cell">${row.m_name}</div>
						<div class="div-cell">${row.m_joindate}</div>
						<div class="div-cell">${row.m_addr}</div>
						<div class="div-cell">${row.m_birth}</div>
						<div class="div-cell">${row.m_mbti}</div>
						<div class="div-cell">
							<c:choose>
								<c:when test="${row.m_gender eq 1}">🙆🏻‍♂️</c:when>
								<c:otherwise>🙆🏻‍♀️</c:otherwise>
							</c:choose>
						</div>
						<div class="div-cell">
							<select id="grade" name="grade" onchange="gradeCh(${row.m_no}, '${row.m_name}', this.value)">
								<optgroup label="로그인 불가">
									<option value="0" <c:if test="${row.m_grade  eq 0 }">selected="selected"</c:if> >강퇴 회원</option>
									<option value="1" <c:if test="${row.m_grade  eq 1 }">selected="selected"</c:if> >탈퇴 회원</option>
								</optgroup>
								<optgroup label="로그인 가능">
									<option value="2" <c:if test="${row.m_grade eq 2 }">selected="selected"</c:if> >일반 회원</option>
									<option value="3" <c:if test="${row.m_grade eq 3 }">selected="selected"</c:if> >실버 회원</option>
									<option value="4" <c:if test="${row.m_grade eq 4 }">selected="selected"</c:if> >골드 회원</option>
									<option value="5" <c:if test="${row.m_grade eq 5 }">selected="selected"</c:if> >VIP 회원</option>
									<option value="6" <c:if test="${row.m_grade eq 6 }">selected="selected"</c:if> >프리미엄 회원</option>
								</optgroup>
								<optgroup label="관리자">
									<option value="7" <c:if test="${row.m_grade eq 7 }">selected="selected"</c:if> >일반 관리자</option>
									<option value="8" <c:if test="${row.m_grade eq 8 }">selected="selected"</c:if> >콘텐츠 관리자</option>
									<option value="9" <c:if test="${row.m_grade eq 9 }">selected="selected"</c:if> >최고 관리자</option>
								</optgroup>
							</select>
						</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>