package com.kh.spring.board.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Reply {
	private int replyNo;
	private String replyContent;
	private String refBno; 
	private String replyWriter;
	private String createDate;
	private String status;
}
