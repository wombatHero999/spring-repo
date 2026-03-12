package com.kh.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.BoardExt;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.board.model.vo.BoardType;

public interface BoardDao {

	Map<String, String> getBoardTypeMap();

	List<Board> selectList(Map<String, Object> paramMap);

	int selectListCount(Map<String, Object> paramMap);

	int insertBoard(Board b);

	int insertBoardImg(BoardImg bi);

	int insertBoardImgList(List<BoardImg> imgList);

	BoardExt selectBoard(int boardNo);

	int increaseCount(int boardNo);

	int updateBoard(Board board);


	int updateBoardImg(BoardImg bi);

	int deleteBoardImg(String deleteList);

	List<String> selectFileList();

	List<BoardType> selectBoardTypeMap();

}
