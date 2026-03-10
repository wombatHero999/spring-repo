package com.kh.spring.chat.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.chat.model.service.ChatService;
import com.kh.spring.chat.model.vo.ChatMessage;
import com.kh.spring.chat.model.vo.ChatRoom;
import com.kh.spring.chat.model.vo.ChatRoomJoin;
import com.kh.spring.security.model.vo.MemberExt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/chat")
@RequiredArgsConstructor
@SessionAttributes({"chatRoomNo"})
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
	
	// 채팅방 참여기능
	@GetMapping("/room/{chatRoomNo}")
	public String joinChatRoom(
			@PathVariable("chatRoomNo") int chatRoomNo, 
			Model model, 
			Authentication auth,
			ChatRoomJoin join
			) {
		/*
		 * 업무로직
		 * 1. 채팅방 번호를 통해 채팅방 메세지 내용 조회
		 * 2. 참여자수 증가(chatRoomJoin테이블에 데이터 등록)
		 * 3. 채팅방 메세지를 model에 추가후 forward
		 *  */
		MemberExt user = (MemberExt) auth.getPrincipal();
		join.setUserNo(user.getUserNo());
		join.setChatRoomNo(chatRoomNo);
		
		List<ChatMessage> list = chatService.joinChatRoom(join);
		
		model.addAttribute("list",list);
		model.addAttribute("loginUser", user);
		model.addAttribute("chatRoomNo", chatRoomNo);//seesion스코프에 보관하기
		
		return "chat/chatRoom";
	}
	
	
	
	
	
	
	
}





