<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer{
		background:black;
		color:white;
		width:1000px;
		margin:auto;
		margin-top:50px;
	}
	
	#enroll-form table {margin:auto;}
	#enroll-form input {margin:5px;}
	
	.error{
		color:red;
		font-size: 0.9em;
		margin-left : 10px;
	}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp"></jsp:include>
	
	
	<div class="outer">
		<br>
		<h2 align="center">회원가입</h2>
		
		<%--
			<form:form>
			 - form태그에 데이터를 자동으로 바인딩해주는 태그
			 - 코드의 가독성과 유지보수성 향상을 위해 사용한다.
			 - form태그에 바인딩할 객체는 서버에서 model 추가해서 넣어준다.
			 
			 form태그의 핵심 기능
			 1. 유효성 검사 실패시 입력값 유지 기능
			 2. 에러메세지 자동 표시 기능
			 
			 3. csrf공격 방어 기능(중요) 
		 --%>
		
		<form:form modelAttribute="member" 
			id="enroll-form" action="${contextPath }/security/insert" method="post">
			<!-- 회원가입form안에.txt -->
			<table align="center">
				<tr>
					<td>* ID</td>
					<td><form:input path="userId" required="required" />
						<button type="button" onclick="idCheck();">아이디중복체크</button>
						<form:errors path="userId" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>* PWD</td>
					<td>
						<form:password path="userPwd" required="required" />
						<form:errors path="userPwd" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>* NAME</td>
					<td>
						<form:input path="userName" required="required" />
						<form:errors path="userName" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;EMAIL</td>
					<td>
						<form:input path="email"/>
						<form:errors path="email" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;BIRTHDAY</td>
					<td>
						<form:input path="birthday" placeholder="생년월일(6자리)"/>
						<form:errors path="birthday" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;GENDER</td>
					<td align="center">
						<form:radiobutton path="gender" value="M"/> 남
						<form:radiobutton path="gender" value="F"/> 여
						<form:errors path="gender" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;PHONE</td>
					<td>
						<form:input path="phone" placeholder="-포함"/>
						<form:errors path="phone" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;ADDRESS</td>
					<td>
						<form:input path="address"/>
						<form:errors path="address" cssClass="error"/>
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<button type="reset">초기화</button>
				<button type="submit">회원가입</button>
			</div>
		</form:form>
	</div>
	
	<script>
		function idCheck(){
			//사용자가 입력한 id값을 가지고 db서버에 이미 존재하는 id인지 확인하는 기능
			const $userId = $("#enroll-form input[name=userId]");
			
			$.ajax({
				url : "/spring/member/idCheck" ,
				data : {
					userId : $userId.val()
				},
				success : function(result){
					if(result == 1){ // 이미 사용중인
						alert("이미 사용중임");
						$userId.val("");
						$userId.focus();
					}else{ // 사용안함
						alert("사용해도 됩니다.");
					}
				}
			})
			
		}
	</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>