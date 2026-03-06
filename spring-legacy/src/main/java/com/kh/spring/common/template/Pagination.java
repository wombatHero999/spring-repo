package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {
	
	public static PageInfo getPageInfo(
			int listCount , int currentPage, 
			int pageLimit, int boardLimit) {
		PageInfo pi = new PageInfo();
		pi.setBoardLimit(boardLimit);
		pi.setPageLimit(pageLimit);
		pi.setListCount(listCount);
		pi.setCurrentPage(currentPage);
		
		// 1. maxPage(최대 페이지 개수)
		int maxPage = (int) Math.ceil(listCount / (double)boardLimit);
		
		// 2. startPage(페이징바의 시작 페이지)
		int startPage = (currentPage -1) / pageLimit * pageLimit +1;
		
		// 3. endPage(페이징바의 종료 페이지)
		int endPage = startPage + pageLimit -1; // 1 + 10 - 1
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		pi.setStartPage(startPage);
		pi.setEndPage(endPage);
		pi.setMaxPage(maxPage);
		
		return pi;
	}
	
	
	
	
	
	
	
	
	
}
