package com.kh.spring.chat.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.security.model.vo.MemberExt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	
	@GetMapping("/chatRoomList")
	public String selectChatRoomList(Model model) {
		List<ChatRoom> list = chatService.selectChatRoomList();
		model.addAttribute("list",list);
		
		return "chat/chatRoomList";
	}
	
	@PostMapping("/openChatRoom")
	public String openChatRoom(
			@ModelAttribute ChatRoom room,
			Authentication auth,
			RedirectAttributes ra
			) {
		MemberExt m = (MemberExt) auth.getPrincipal();
		room.setUserNo(m.getUserNo());
		
		int result = chatService.openChatRoom(room);
		
		if(result == 0) {
			throw new RuntimeException("채팅방 등록 실패");
		}
		ra.addFlashAttribute("alertMsg", "채팅방 생성 성공");
		
		return "redirect:/chat/chatRoomList";
	}
	
	
	
	
	
	
	
	
}





