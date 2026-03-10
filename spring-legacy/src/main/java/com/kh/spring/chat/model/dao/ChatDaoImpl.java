package com.kh.spring.chat.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatDaoImpl implements ChatDao{
	
	private final SqlSessionTemplate session;
	
	@Override
	public List<ChatRoom> selectChatRoomList() {
		return session.selectList("chat.selectChatRoomList");
	}

	@Override
	public int openChatRoom(ChatRoom room) {
		return session.insert("chat.openChatRoom" , room);
	}

	@Override
	public int joinCheck(ChatRoomJoin join) {
		return session.selectOne("chat.joinCheck", join);
	}

	@Override
	public int joinChatRoom(ChatRoomJoin join) {
		return session.insert("chat.joinChatRoom", join);
	}

	@Override
	public List<ChatMessage> selectChatMessage(ChatRoomJoin join) {
		return session.selectList("chat.selectChatMessage", join);
	}
}




