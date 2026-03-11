//1. 채팅 메세지 보내기 기능
document.getElementById("send")
.addEventListener("click",sendMessage);

function sendMessage(){
    var input = document.getElementById("inputChatting");

    if(input.value.trim().length == 0){
        alert("1글자 이상 입력하세요");
        input.value = "";
        input.focus();
        return;
    }

    var chatMessage = {
        message : input.value , 
        chatRoomNo ,
        userNo , 
        userName
    };

    var json = JSON.stringify(chatMessage);
    chattingSocket.send(json);

    input.value = "";
}
