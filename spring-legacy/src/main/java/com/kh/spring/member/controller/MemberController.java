package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // ComponentScan에 의해 bean객체로 등록시키게하는 속성
@RequestMapping("/member")
@SessionAttributes({"loginUser"})
// model에 들어가는 데이터 중, Session에 보관시킬 데이터를 설정하는 주석
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
	@RequestMapping(value = "/login", method= RequestMethod.GET)
	public String loginMember() {
		return "member/login";
	}
	
	
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
//	public String login(Member m) {
//		System.out.println(m);
//		return "home";
//	}
//	public ModelAndView login(Member m , ModelAndView mv , Model model) {
//		/* 
//		 * 응답데이터를 담을 수 있는 객체.(Model)
//		 * 1) Model 
//		 *  - 포워딩할 응답 뷰페이지에 전달하고자 하는 데이터를 맵형식으로 담을 수 있는 객체.
//		 *  - 기본적으로 request scope를 가지고 있으며, 설정을 통해 session scope에
//		 *  도 데이터를 담을 수 있다.
//		 *  - 클래스 선언부에 @SessionAttributes를 추가하면 데이터가 session scope에
//		 *  저장된다.
//		 *  
//		 * 2) ModelAndView
//		 *  - ModelAndView에서 Model은 Model데이터를 의미한다. 
//		 *  - VIEW는 이동할 페이지에 대한 정보를 담고 있는 객체를 의미한다.
//		 *  - 기본 requestScope에 데이터를 보관.
//		 * */
//		model.addAttribute("errorMsg","오류발생");
//		
//		mv.addObject("errorMsg","오류발생!");
//		mv.setViewName("common/errorPage");
//		
//		return mv;
//	}
	//@RequestMapping(value = "/member/login", method= RequestMethod.POST)
	@PostMapping("/login")
	public ModelAndView login(
			@ModelAttribute Member m,
			ModelAndView mv , 
			Model model , 
			HttpSession session, 
			RedirectAttributes ra
			) {
		// 로그인 요청 처리
		Member loginUser = mService.loginMember(m);
		
		// 로그인 성공
		if(loginUser != null) {
			// 인증된 사용자 정보를 session에 보관
			// session.setAttribute("loginUser", loginUser);
			model.addAttribute("loginUser", loginUser);
		}else {
			// session.setAttribute("alertMsg", "로그인 실패");
			ra.addFlashAttribute("alertMsg","로그인 실패");
			/* 
			 * RedirectAttributes의 flashAttribute는 데이터를 우선
			 * SessionScope에 담았다가, 리다이렉트가 완료되면 SessionScope
			 * 의 데이터를 request Scope로 변경해준다.
			 * */
		}
		mv.setViewName("redirect:/");// 메인페이지로 리다이렉트.
		
		return mv;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, SessionStatus status) {
		// 로그아웃
		//  - 현재 세션의 "인증"정보를 만료시키는 행위
		
		// session내부의 모든 데이터를 삭제시키는 메서드
		//session.invalidate();
		status.setComplete(); // model로 sessionScope에 이관된 데이터를 삭제하는 메서드
		
		return "redirect:/";
	}
	
	@GetMapping("/insert")
	public String enrollForm() {
		return "member/memberEnrollForm";
	}
	
	@PostMapping("/insert")
	public String insertMember(
			Member m , 
			Model model , 
			RedirectAttributes ra
			) {
		int result = mService.insertMember(m);
		
		if(result > 0) {
			ra.addFlashAttribute("alertMsg","회원가입 성공");
			return "redirect:/member/login";
		}else {
			throw new RuntimeException();
//			model.addAttribute("errorMsg","회원가입에 실패했습니다..");
//			return "common/errorPage";
		}
	}
	
	// 비동기 요청 처리
	@ResponseBody // 반환값을 jsp가 아닌, 반환해야할 값으로 처리하게하는주석
	@GetMapping("/idCheck")
	public int idCheck(String userId) {
		int result = mService.idCheck(userId);
		return result;
	}
	
//	@ResponseBody
//	@GetMapping("/selectOne")
//	public Member selectOne(String userId) {
//		Member searchMember = mService.selectOne(userId);
//		
//		// Jackson-databind를 활용하여 , VO클래스, 컬렉션 데이터를 자동으로
//		// JSON으로 반환해줄 수 있다.
//		return searchMember;
//	}
	
	@GetMapping("/selectOne")
	public ResponseEntity<Member> selectOne(String userId){
		Member searchMember = mService.selectOne(userId);
		
		ResponseEntity<Member> res = null;
		if(searchMember != null) {
			// ok() : 응답상태 200
			res = ResponseEntity.ok(searchMember);
		}else {
			// 응답상태 : 404
			res = ResponseEntity
					.notFound()
					.build();
		}
		return res;
	}
	
	/*
	 * 스프링 예외처리 방법
	 * 1. try ~ catch로 메서드별 예외처리 -> 1순위로 적용
	 * 2. 하나의 컨트롤러에서 발생하는 예외들을 모아서 처리하는 방법
	 *   - 컨트롤러에 예외처리 메서드를 1개 추가한 후, @ExceptionHandler어노테이션
	 *     추가.
	 *   - 2순위로 적용
	 * 3. 어플리케이션 전역에서 발생하는 예외를 모아서 처리하는 방법 
	 *   - 새로운 클래스 작성후 ControllerAdvice 어노테이션 추가
	 *   - 3순위 
	 *  */
	
	
	
	
	
	
	
	
	
}





