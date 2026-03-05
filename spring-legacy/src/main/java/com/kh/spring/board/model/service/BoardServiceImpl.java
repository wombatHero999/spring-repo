package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDao boardDao;
	
	@Override
	public Map<String, String> getBoardTypeMap() {
		return boardDao.getBoardTypeMap();
	}

	@Override
	public List<Board> selectList(Map<String, Object> paramMap) {
		return boardDao.selectList(paramMap);
	}

	
}





