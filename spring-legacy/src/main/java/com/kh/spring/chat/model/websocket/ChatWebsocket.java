package com.kh.spring.chat.model.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.chat.model.vo.ChatMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ChatWebsocket extends TextWebSocketHandler {
	// TextWebSocketHandler
	//  -> 텍스트기반의 메세지 처리를 위한 메세드를 제공하는 클래스.
	private final ChatService cService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	// 채팅방별 데이터 관리를 위한 Map객체 생성
	private final Map<Integer, Set<WebSocketSession>> roomSession
		= new HashMap<>();
	
	//클라이언트와 서버간에 웹소켓 연결이 완료된 이후에 실행되는 함수.
	@Override
	public void afterConnectionEstablished(
			WebSocketSession session) throws Exception {
		/* 
		 * 클라이언트와 연결시, session안에 담겨있는 "채팅방 번호"를 꺼내고, 이를
		 * 현재 서버상에 저장.
		 * 
		 * WebSocketSession
		 *  - 클라이언트가 웹소켓을 통해 연결하고 있는동안만 유지되는 세션
		 *  - 사용자가 페이지를 새로고침하거나 브라우저를 닫으면 세션은 끊기고,
		 *  새로운 세션이 생성된다.
		 * */
		int chatRoomNo = (int) 
				session.getAttributes().get("chatRoomNo");
		log.debug("웹소켓 연결 완료. 세션 id : {}, 채팅방 번호 : {}" 
				, session.getId(), chatRoomNo);
		 Set<WebSocketSession> sessions
		 = roomSession.get(chatRoomNo);
		// 현재 채팅방번호에 set객체가 없는경우, 새롭게 생성후 데이터 추가. 
		if(sessions == null) {
			sessions = new HashSet<>();
			roomSession.put(chatRoomNo, sessions);
		}
		sessions.add(session);
	}
	
	// 클라이언트가 메세지를 전달하는 경우 실행되는 함수
	@Override
	protected void handleTextMessage
	(WebSocketSession session, TextMessage message) throws Exception {
		// TextMessage : 웹소켓을 이용해 전달된 데이터가 담긴 객체
		log.debug("메시지 {} ", message);
		log.debug("메시지 body {} ", message.getPayload());
		
		// JSON -> VO
		ChatMessage chatMessage = 
				objectMapper.readValue(
						message.getPayload(), ChatMessage.class);
		// 전달받은 메세지를 DB상에 등록
		int result = cService.insertMessage(chatMessage);
		String current = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		chatMessage.setCreateDate(current);
		if(result > 0) {
			// 전달받은 메세지를 "같은 채팅방"에 접속 중인 사용자들에게 전송
			Set<WebSocketSession> roomSet 
				= roomSession.get(chatMessage.getChatRoomNo());
			if(roomSet != null) {
				for(WebSocketSession s : roomSet) {
					String json = 
							objectMapper.writeValueAsString(chatMessage);
					s.sendMessage( new TextMessage(json)  );
				}
			}
		}
	}
	
	// 웹소켓 연결 종료 후실행되는 메서드
	// 클라이언트의 session정보를 메모리상에서 제거할 예정
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		int chatRoomNo = (int) session.getAttributes().get("chatRoomNo");
		log.debug("웹소켓 연결 종료");
		Set<WebSocketSession> roomSet = roomSession.get(chatRoomNo);
		if(roomSet != null) {
			roomSet.remove(session);
			
			if(roomSet.isEmpty()) {
				roomSession.remove(chatRoomNo);
			}
		}
	}
	
	
	

}






