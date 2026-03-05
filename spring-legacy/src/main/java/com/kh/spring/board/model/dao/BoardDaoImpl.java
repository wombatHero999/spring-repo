package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao{
	
	private final SqlSessionTemplate session;
	
	@Override
	public Map<String, String> getBoardTypeMap() {
		/*
		 * selectMap
		 *  - Map<KEY, VALUE>형태의 값을 반환하는 메서드
		 *  - 두번째 매개변수로는 어떤 칼럼을 KEY값으로 사용할지를 작성한다.
		 *  - SELECT KEY, VALUE FROM TABLE
		 *  */
		return session.selectMap("board.getBoardTypeMap", "boardCd");
	}

	@Override
	public List<Board> selectList(Map<String, Object> paramMap) {
		
		return session.selectList("board.selectList", paramMap);
	}

}






