package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.ReplyDao;
import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
	
	private final ReplyDao rDao;
	
	@Override
	public int insertReply(Reply r) {
		return rDao.insertReply(r);
	}

	@Override
	public List<Reply> selectList(int boardNo) {
		return rDao.selectList(boardNo);
	}

	@Override
	public int deleteReply(Reply r) {
		return rDao.deleteReply(r);
	}

}





