package com.kh.spring.board.model.vo;

import java.util.List;

import lombok.Data;

// 상세보기용 vo
@Data
public class BoardExt extends Board{
	private List<BoardImg> imgList;
	private String userName;
}





