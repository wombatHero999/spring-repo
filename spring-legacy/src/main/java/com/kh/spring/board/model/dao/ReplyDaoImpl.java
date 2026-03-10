package com.kh.spring.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Reply;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReplyDaoImpl implements ReplyDao {
	
	private final SqlSessionTemplate session;
	
	@Override
	public int insertReply(Reply r) {
		return session.insert("board.insertReply" , r);
	}

	@Override
	public List<Reply> selectList(int boardNo) {
		return session.selectList("board.selectReplyList",boardNo);
	}

	@Override
	public int deleteReply(Reply r) {
		return session.update("board.deleteReply", r);
	}
	
	
	
}








