/* 
    stomp 동작방식
    1. 클라이언트가 SockJS를 이용해서 서버에 연결요청을 보낸다.
     - 연결 수락시 서버는 Conntected프레임을 웹소켓을 통해 반환
    2. 클라이언트는 서버와 연결 수립 후 구독중인 url목록을 전달.
    3. 구독중인 url로 메세지가 전달되는 경우 브로커는 이 url을 구독중인
    모든 클라이언트에게 메세지를 브로드캐스트한다.
*/
// 스톰프클라이언트로 연결요청을 보낸 후, 연결이 완료되면 실행할 이벤트 핸들러
stompClient.connect({}, function(e){
    console.log(e);

    //현재 클라이언트가 구독중인 url목록들을 전달

    // 현재 채팅방에 새로운 사용자가 입장하거나, 퇴장하는 경우를 구독
    stompClient.subscribe("/topic/room/"+chatRoomNo , function(message){
        const chatMessage = JSON.parse(message.body);
        showMessage(chatMessage);
    })

    // 입장메세지 서버로 전송
    stompClient.send("/app/chat/enter/"+chatRoomNo , {} , JSON.stringify({
        userName , 
        chatRoomNo, 
        userNo 
    }));

})

function showMessage(message){
    const li = document.createElement("li");
    const p = document.createElement("p");

    p.classList.add("chat");
    p.innerText = message.message;
    p.style.color = "gray";
    p.style.textAlign = "center";
    li.append(p);

    document.querySelector(".display-chatting").append(li);
}

// 나가기 버튼
const exitBtn = document.querySelector("#exit-btn");

exitBtn.onclick = function(){
    /*
        나가기 버튼 클릭시
        1. 같은 방에 접속중인 사용자에게 퇴장 메세지 전송
        2. CHAT_ROOM_JOIN테이블에서 한행의 데이터를 삭제
        3. 현재 채팅방에 참여자가 0명이라면, 채팅방 자체를 삭제
    */
    stompClient.send("/app/chat/exit/"+chatRoomNo, {} , JSON.stringify({
        userName ,
        chatRoomNo , 
        userNo 
    }))

    stompClient.disconnect(function(){
        location.href = `${contextPath}/chat/chatRoomList`;
    })
}
