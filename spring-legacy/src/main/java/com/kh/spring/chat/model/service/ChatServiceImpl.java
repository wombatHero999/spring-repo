package com.kh.spring.chat.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.chat.model.dao.ChatDao;
import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	private final ChatDao chatDao;
	
	@Override
	public List<ChatRoom> selectChatRoomList() {
		return chatDao.selectChatRoomList();
	}

	@Override
	public int openChatRoom(ChatRoom room) {
		return chatDao.openChatRoom(room);
	}

	@Override
	public List<ChatMessage> joinChatRoom(ChatRoomJoin join) {
		// 현재회원이 해당 채티방에 이미 참여하고 있는지 확인.
		int result = chatDao.joinCheck(join);// 참여 중이라면 1, 아니라면 0
		
		if(result == 0) {
			//참여자 정보를 insert
			result = chatDao.joinChatRoom(join);
		}
		
		// insert 성공시 list반환. 실패시 null반환
		List<ChatMessage> list = null;
		if(result > 0) {
			list = chatDao.selectChatMessage(join);
		}
		return list;
	}

	@Override
	public int insertMessage(ChatMessage chatMessage) {
		return chatDao.insertMessage(chatMessage);
	}

}




