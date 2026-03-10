package com.kh.spring.chat.model.dao;

import java.util.List;

import com.kh.spring.chat.model.vo.ChatRoom;

public interface ChatDao {

	List<ChatRoom> selectChatRoomList();

	int openChatRoom(ChatRoom room);

}
