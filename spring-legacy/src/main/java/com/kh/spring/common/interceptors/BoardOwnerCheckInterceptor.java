package com.kh.spring.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.BoardExt;
import com.kh.spring.security.model.vo.MemberExt;

public class BoardOwnerCheckInterceptor implements HandlerInterceptor {
	
	@Autowired
	private BoardService service;
	
	// 전처리 함수 : 컨트롤러가 서블릿의 요청을 처리하기 "전"에 먼저 실행되는 함수
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. 현재 요청을 보낸 사용자의 정보를 추출.
		Authentication auth = SecurityContextHolder
								.getContext().getAuthentication();
		MemberExt loginUser = (MemberExt) auth.getPrincipal(); 
		
		// 2. Admin권한을 가진 경우 항상 통과
		if(auth.getAuthorities()
				.stream()
				.anyMatch( authority -> 
				authority.getAuthority().equals("ROLE_ADMIN"))) {
			return true;
		}
		
		// 3. 요청 url 에서 게시글 번호를 추출하여, 게시글 작성자 정보 조회
		//  /board/update/boardNo
		String [] uri = request.getRequestURI().split("/");
		int boardNo = Integer.parseInt( uri[uri.length -1] );
		
		BoardExt board = service.selectBoard(boardNo);
		if(board == null) {
			return false;
		}
		int boardWriter = Integer.parseInt(board.getBoardWriter());
		
		if(boardWriter != loginUser.getUserNo()) {
			response.sendRedirect
			(request.getContextPath()+"/security/accessDenied");
			return false;
		}
		return true;		
	}
	
	
	
}
