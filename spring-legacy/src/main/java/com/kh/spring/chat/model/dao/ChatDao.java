package com.kh.spring.chat.model.dao;

import java.util.List;

import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;

public interface ChatDao {

	List<ChatRoom> selectChatRoomList();

	int openChatRoom(ChatRoom room);

	int joinCheck(ChatRoomJoin join);

	int joinChatRoom(ChatRoomJoin join);

	List<ChatMessage> selectChatMessage(ChatRoomJoin join);

	int insertMessage(ChatMessage chatMessage);

	int exitChatRoom(ChatMessage message);

	int countChatRoomMember(ChatMessage message);

	int closeChatRoom(ChatMessage message);

}
