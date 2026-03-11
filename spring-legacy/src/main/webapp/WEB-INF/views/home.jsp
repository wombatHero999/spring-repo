<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

    <div class="content">
        <div class="content-1">
            <h3>회원 정보 조회</h3>

            <p>아이디를 입력 받아 일치하는 회원 정보를 출력</p>

            아이디 : <input type="text" id="in1">
            <button id="select1">조회</button>
            <div id="result1" style="height: 150px"></div>
        </div>
        
        <!-- 
        	관리자 공지기능
        	관리자가 알람내용을 입력 후, "보내기"버튼을 누르면 현재 사이트에
        	접속중인 모든 사용자에게 알림내용이 출력되도록 설정.
         -->
        <sec:authorize access="hasRole('ROLE_ADMIN')">
			<div class="content-2">
				<input type="text" id="notice" placeholder="공지내용을입력하세요.">
				<button id="send-notice-btn">공지보내기</button>
			</div>      
			
			<script>
				$(function(){
					const stompClient = Stomp.over(new SockJS('${contextPath}/stomp'));
					
					stompClient.connect({},function(){
						const sendBtn = document.querySelector("#send-notice-btn");
						sendBtn.onclick = function(){
							const content = document.querySelector("#notice").value;
							stompClient.send("/app/notice/send",{},content);
						}
					})
				})
			</script>
			  
        </sec:authorize>
		
		<script>
		document.getElementById("select1").addEventListener("click", function(){
			const in1 = document.getElementById('in1');
			const result1 = document.getElementById('result1');
			
			$.ajax({
				url : '/spring/member/selectOne',
				data : {userId : in1.value},
				type : 'GET',				
				success : function (result){
					//result => {userId : ?? , userName : ?? }
					
					result1.innerHTML = "";
					
					//let result = userInfo;
					
					console.log(result);
					
					//result = JSON.parse(result);
					
					//console.log(result);
					
					if(result.userId){
						//1) ul요소 생성
						const ul = document.createElement("ul"); // <ul></ul>
						
						//2) li요소 생성 *2개
						const li1 = document.createElement("li");
						li1.innerText= "아이디 : "+result.userId;
						
						const li2 = document.createElement("li");
						li2.innerText= "이름  :"+result.userName;
						
						//3) ul에 li추가
						ul.append(li1, li2);
						
						//4) ul을 div에 추가
						result1.append(ul);
					}
				}
			})
		})
	</script>
	
		
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
</body>
</html>