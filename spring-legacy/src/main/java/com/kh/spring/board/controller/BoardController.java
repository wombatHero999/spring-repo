package com.kh.spring.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;
import com.kh.spring.common.util.Utils;
import com.kh.spring.security.model.vo.MemberExt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final ResourceLoader resourceLoader;
	private final ServletContext application; // application scope를 가진 객체
	/*
	 * ResourceLoader
	 *  - 스프링에서 제공하는 자원 로딩 클래스
	 *  - classpath, file시스템, url등 다양한 경로상의 자원을
	 *  "동일한"인터페이스로 로드(입력)하는 메서드를 제공한다.
	 *  */
	
	// BoardType전역객체 설정
	//  - 어플리케이션 전역에서 사용할 수 있는 BoardType객체 추가
	//  - 서버 가동중 1회만 수행되도록 설정
	@PostConstruct
	public void init() {
		// key=코드 , value=게시판이름
		Map<String, String> boardTypeMap
			= boardService.getBoardTypeMap();
		application.setAttribute("boardTypeMap", boardTypeMap);
		log.debug("boardTypeMap : {}" , boardTypeMap);
	}
	
	
	@GetMapping("/list/{boardCode}")
	public String selectList(
			@PathVariable("boardCode") String boardCode
			/* 
			 * @PathVariable
			 *  - N,P,C,M등 동적으로 바뀌는 모든 동적 경로 변수를 바인딩 해준다.
			 *  - 선언한 동적 경로변수는 @PathVariable로 추출하여 사용할 수있다.
			 *  - @PathVariable로 추출한 자원은 자동으로 model영역에 추가된다.
			 * */,
			 @RequestParam(value="currentPage" , defaultValue = "1")
				int currentPage,
			 // 현재 요청한 페이지 번호, 기본값을 1로 처리하여 값을 전달하지 않은 경우 항상
			 // 1페이지를 요청하도록 처리하였음.
			 Model model ,
			 @RequestParam Map<String, Object> paramMap
			 /* 
			  * @RequestParam Map<String,Object>
			  *  - 클라이언트가 전달한 파라미터의 key, value값을 Map형태로 만들어 바인딩
			  *  - 현재메서드로 전달할 파라미터의 개수가 "정해지지 않은 경우" 혹은 일반적인 VO클래스로
			  *  바인딩되지 않는 경우 사용한다.(검색파라미터)
			  *  - 반드시 @RequestParam을 추가해줘야 바인딩해준다.
			  * */
			) {
		/* 
		 * 업무로직
		 * 1. 페이징처리
		 * 	  1) 현재 요청한 게시판 코드와 검색정보와 일치하는 게시글의 "총 개수" 조회
		 *    2) 게시글 갯수, 페이지 번호, 기본 파라미터들을 추출하여 페이징정보를 가진 객체 생성 	
		 * 2. 현재 요청한 게시판 코드와 일치하면서, 현재 요청한 페이지에 맞는 게시글 정보를 조회
		 * 3. 게시글정보, 페이징정보, 검색정보를 담아서 forward
		 * */
		// paramMap안에 데이터 조회에 필요한 모든 정보 저장
		paramMap.put("boardCd", boardCode);
		
		int boardLimit = 10;
		int pageLimit = 10;
		int listCount = boardService.selectListCount(paramMap);
		
		PageInfo pi = 
				Pagination
				.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		log.debug("pi : {}",pi);
		paramMap.put("pi", pi);
		
		List<Board> list = boardService.selectList(paramMap);
		model.addAttribute("list",list);
		model.addAttribute("pi",pi);
		
		return "board/boardListView";
	}
	
	
	// 게시판 등록폼 이동
	@GetMapping("/insert/{boardCd}")
	public String enrollForm(
			@PathVariable("boardCd") String boardCd,
			@ModelAttribute Board b,
			Model m
			) {
		m.addAttribute("b",b);
		return "board/boardEnrollForm";	
	}
	
	@PostMapping("/insert/{boardCd}")
	public String insertBoard(
			@ModelAttribute Board b, 
			@PathVariable("boardCd") String boardCd,
			Model model,
			RedirectAttributes ra,
			@RequestParam(value="upfile" , required = false ) List<MultipartFile> upfiles
			/* 
			 * List<MultipartFile>
			 *  - MultipartFile
		 *  		- multipart/form-data방식으로 전송된 파일데이터를 바인딩해주는 클래스
			 *  - 파일의 이름, 크기, 존재여부, 저장기능 등 다양한 메서드 제공
			 *  @RequestParam(value="upfile" , required = false )
			 *  - name속성값이 upfile로 전달되는 모든 파일을 하나의 컬렉션으로 모아준다.
			 *  - @RequestParam + List/Map사용시 바인딩할 데이터가 없더라도 항상
			 *  객체 자체는 생성한다.
			 * */
			) {
		/*
		 * 업무로직
		 * 1. 유효성검사(생략)
		 * 2. 첨부파일 존재여부 확인
		 *    1) 첨부파일이 존재한다면 web서버상에 첨부파일 저장
		 *    2) 존재하지 않는다면 2번과정 패스
		 * 3. 게시판정보 등록 및 첨부파일 정보 db등록을 위한 서비스 호출   
		 * 4. 전체 처리 결과에 따른 view페이지 지정 
		 *    1) 성공시에는 목록페이지로 리디렉트.
		 *    2) 실패시 에러 강제 발생 -> ControllerAdvice가 처리
		 *  */
		// 첨부파일 존재 여부 체크
		//  첨부파일이 존재한다면 여기에 데이터를 담아, dao까지 전달할 예정
		List<BoardImg> imgList = new ArrayList<>();
		int level = 0;// 첨부파일 레벨 설정
		for(MultipartFile upfile : upfiles ) {
			if(upfile.isEmpty()) {
				continue;
			}
			// 첨부파일이 존재한다면 WEB서버 상에 첨부파일 저장.
			String changeName = 
					Utils.saveFile(upfile, application, boardCd);
			// 첨부파일 관리를 위해 DB에 첨부파일 위치정보 저장
			BoardImg bi = new BoardImg();
			bi.setChangeName(changeName);
			bi.setOriginName(upfile.getOriginalFilename());
			bi.setImgLevel(level++);
			// refBno는 service에서 바인딩 필요.
			imgList.add(bi);
		}
		
		// 게시글 등록 서비스 호출
		//  - 게시글 등록 서비스 호출 전, 게시글 정보 바인딩
		//  - 회원번호, 게시판 코드
		b.setBoardCd(boardCd);
		MemberExt m = (MemberExt) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();
		b.setBoardWriter( m.getUserNo()+"" );
		
		log.debug("board : {}" , b);
		log.debug("imgList : {}",imgList);
		int result = boardService.insertBoard(b, imgList);
		
		if(result <= 0) {
			throw new RuntimeException("게시글 작성 실패");
		}
		
		ra.addFlashAttribute("alertMsg","게시글 작성 성공");
		return "redirect:/board/list/"+boardCd;
	}
	
	
}








