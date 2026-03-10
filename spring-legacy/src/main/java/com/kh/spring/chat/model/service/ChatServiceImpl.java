package com.kh.spring.chat.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.chat.model.dao.ChatDao;
import com.kh.spring.chat.model.vo.ChatRoom;

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

}




