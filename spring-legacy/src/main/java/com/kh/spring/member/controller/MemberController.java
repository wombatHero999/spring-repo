package com.kh.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // ComponentScan에 의해 bean객체로 등록시키게하는 속성
public class MemberController {

	@Autowired // 의존성주입
	private MemberService mService; // = new MemberServiceImpl();
	/* 
	 * Spring DI(Dependency Injection)
	 *  - 의존성 주입
	 *  - 어플리케이션을 구성하는 객체를 개발자가 직접 생성하는게 아닌, 스프링이 생성한
	 *  객체(bean)를 주입받아서 생성하는 방식
	 *  - new연산자를 직접 사용하지 않고, Autowird 어노테이션을 통해 주입받는다.
	 * */
	@RequestMapping(value = "/member/login", method= RequestMethod.GET)
	public String loginMember() {
		return "member/login";
	}
	
	@RequestMapping(value = "/member/login", method= RequestMethod.POST)
//	public String login(HttpServletRequest req) {
//		System.out.println(req.getParameter("userId"));
//		System.out.println(req.getParameter("userPwd"));
//		return "home";
//	}
	/* 
	 * @RequestParam
	 *  - Servlet의 request.getParameter(키)를 대신해주는 역할의 어노테이션
	 *  - 클라이언트가 요청한 값을 대신 변환하여 바인딩 해주는 역할은 argumentResolver
	 *  가 수행한다. 
	 * */
//	public String login(
//			@RequestParam(value="userId", defaultValue="mkm")
//			String userId) {
//		System.out.println(userId);
//		return "home";
//	}
	// 매개변수의 이름과 "일치"하는 request의 파라미터값을 추출하여 자동 바인딩.
	// 만약 일치하는 파라미터가 없다면 null값으로 바인딩
//	public String login(String userId, String userPwd) {
//		System.out.println(userId);
//		System.out.println(userPwd);
//		return "home";
//	}
	/* 
	 * 커맨드 객체 방식
	 * @ModelAttribute
	 *  - 메서드의 매개변수로 VO클래스 타입을 지정하는 경우 실행
	 *  - 요청시 전달한 name속성과 일치하는 vo클래스 필드의 setter메서드를
	 *  호출하여 데이터를 바인딩
	 *  - 매개변수 클래스와 일치하는 클래스의 "기본생성자" 호출 후,
	 *    파라미터의 key값과 클래스의 필드명이 "일치"하는 경우 setter호출
	 * */
	public String login(Member m) {
		System.out.println(m);
		return "home";
	}
	
	
	
	
}





