package com.kh.spring.chat.model.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.chat.model.vo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StompController {
	
	private final ChatService service;
	private final SimpMessagingTemplate messagingTemplate;
	/*
	 * SimpMessagingTemplate
	 *  - 서버에서 특정 클라이언트에게 메세지를 전송하기 위한 STOMP템플릿
	 *  - STOMP구독경로로 메시지를 전송할 수 있다
	 *  
	 *  convertAndSend() : 전체 사용자에게 메시지를 보내야하는 경우
	 *  convertAndSendToUser() : 특정 사용자에게 메시지를 보내야 하는 경우
	 *  */
	
	@MessageMapping("/chat/enter/{roomNo}")
	@SendTo("/topic/room/{roomNo}")//구독 url 지정
	public ChatMessage handleEnter(
			@DestinationVariable int roomNo,
			@Payload ChatMessage message
			) {
		message.setType(ChatMessage.MessageType.ENTER);
		message.setMessage(message.getUserName()+"님이 입장하셨습니다.");
		
		// 메세지 브로커에게 메시지 템플릿 전송
		return message;
	}
	
	@MessageMapping("/chat/exit/{roomNo}")
	public void handleExit(
			@DestinationVariable int roomNo,
			@Payload ChatMessage message
			) {
		// 1. 참여자 정보 삭제
		// 2. 채팅방 참여자 수가 0명이라면, 채티방을삭제
		service.exitChatRoom(message);
		
		// 3. 퇴장메시지 담은 후 전송
		message.setType(ChatMessage.MessageType.EXIT);
		message.setMessage(message.getUserName()+"님이 퇴장했습니다.");
		messagingTemplate.convertAndSend("/topic/room/"+roomNo,message);
	}
	
	@MessageMapping("/notice/send")
	@SendTo("/topic/notice")
	public String sendNotice(@Payload String notice) {
		return notice;
	}
	
	
}









