package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardImg;

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

	@Override
	public int selectListCount(Map<String, Object> paramMap) {
		return boardDao.selectListCount(paramMap);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class})
	/* 
	 * @Transactional
	 *  - 선언적 트랜잭션 관리용 어노테이션
	 *  - Exception.class와 하위 예외가 발생하면 무조건 rollback처리한다.
	 *  - rollbackFor를 지정하지 않으면 RuntimeException에러가 발생한 경우만
	 *    rollback 한다.
	 * */
	public int insertBoard(Board b, List<BoardImg> imgList) {
		/* 
		 * 서비스 로직
		 * 0. 게시글 데이터 전처리(개행문자 처리 및 xss핸들링)
		 * 1. board테이블에 데이터 insert
		 * 2. 첨부파일이 존재하는경우 첨부파일 테이블에 insert
		 * 3. 1번, 2번과정에서 실패가 발생하는 경우 rollback
		 * */
		
		// 1. 게시글 저장
		//   - 게시글 insert후, boardNo값을 b객체에 바인딩해줘야 한다.
		int result = boardDao.insertBoard(b);
		
		if(result == 0) {
			throw new RuntimeException("게시글 등록 실패");
		}
		
		// 2.첨부파일 데이터 insert
		if(!imgList.isEmpty()) {
			for(BoardImg bi : imgList) {
				bi.setRefBno(b.getBoardNo());
				// 행단위 insert 수행
//				result = boardDao.inserBoardImg(bi);
//				
//				if(result == 0) {
//					throw new RuntimeException("첨부파일 등록 실패");
//				}
			}
			result = boardDao.insertBoardImgList(imgList); 
			
			if(result != imgList.size()) {
				throw new RuntimeException("첨부파일 등록 에러 발생");
			}
		}
		return result;
	}

	
}





