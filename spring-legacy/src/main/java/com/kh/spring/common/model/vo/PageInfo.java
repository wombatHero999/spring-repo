package com.kh.spring.common.model.vo;

import lombok.Data;

@Data
public class PageInfo {
	private int listCount; // 총 게시글 갯수(데이터베이스에서 조회)
	private int currentPage; // 클라이언트가 요청한 페이지 번호.(클라이언트가 전달)
	private int pageLimit; // 페이징바 갯수
	private int boardLimit; // 한페이지에 보여줄 게시글 갯수
	
	private int maxPage; // 최대 페이지 수
	private int startPage; // 페이징바 시작 수 
	private int endPage; // 페이징바 끝 수
}






