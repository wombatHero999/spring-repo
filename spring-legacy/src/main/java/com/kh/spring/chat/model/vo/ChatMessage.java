package com.kh.spring.chat.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
	private int cmNo;
	private String message;
	private String createDate;
	private int chatRoomNo;
	private int userNo;
	
	private String userName;
	
	//클라이언트의 메세지 상태를 관리할 속성
	public enum MessageType {
		ENTER, EXIT, TALK
	}
	private MessageType type;
	
	
	
	
	
	
	
	
	
	
	
	
}


