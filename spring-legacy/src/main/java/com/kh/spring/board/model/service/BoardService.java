package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.model.vo.BoardType;

public interface BoardService {

	Map<String, String> getBoardTypeMap();

	List<Board> selectList(Map<String, Object> paramMap);

	int selectListCount(Map<String, Object> paramMap);

	int insertBoard(Board b, List<BoardImg> imgList);

	BoardExt selectBoard(int boardNo);

	int increaseCount(int boardNo);

	int updateBoard(Board board, String deleteList, List<BoardImg> imgList);

	List<String> selectFileList();

	List<BoardType> selectBoardTypeMap();

}
