package com.kh.spring.chat.model.service;

import java.util.List;

import com.kh.spring.chat.model.vo.ChatRoom;

public interface ChatService {

	List<ChatRoom> selectChatRoomList();

	int openChatRoom(ChatRoom room);

}
