package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Board;

public interface BoardService {

	Map<String, String> getBoardTypeMap();

	List<Board> selectList(Map<String, Object> paramMap);

}
