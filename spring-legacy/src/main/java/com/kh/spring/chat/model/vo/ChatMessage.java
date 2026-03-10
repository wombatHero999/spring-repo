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
	
}


