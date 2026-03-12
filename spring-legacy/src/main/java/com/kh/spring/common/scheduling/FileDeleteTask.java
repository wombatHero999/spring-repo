package com.kh.spring.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.BoardType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileDeleteTask {
	/*
	 * 파일삭제 스케쥴러
	 *  - 목표 : DB에는 존재하지 않으나, WEB-Server상에만 존재하는 쓸모
	 *  없는 파일을 삭제
	 *  
	 *  1. 데이터베이스에서 BOARD_IMG에 등록된 모든 첨부파일 경로를 조회
	 *  2. 모든 게시판 유형(BOARD_TYPE)을 조회하여, 각각의 게시판 디렉토리
	 *  경로를 탐색
	 *  3. 해당 디렉토리에서 실제 서버에 조재하는 이미지 파일 목록을 수집한다.
	 *  4. 각 파일이 DB에 등록되어 잇는지 여부를 확인
	 *  5. DB에는 존재하지 않는 파일이라면 "삭제"처리 한다.
	 *  6. 이 작업은 유저활동량이 적은 매달 1일 4시에 실행되도록 스케쥴링한다.
	 *  */
	
	private final BoardService service;
	private final ServletContext application;
	
	//@Scheduled( cron = "0 0 4 1 * *")
	//@Scheduled( cron = "1/1 * * * * *")
	public void deleteFile() {
		//1. db에서 데이터 조회
		List<String> list = service.selectFileList();
		//2. 게시판 유형목록 조회
		List<BoardType> typeList = service.selectBoardTypeMap();
		
		for(BoardType type : typeList) {
			String webPath 
				= application.getRealPath("/resources/images/board/"+type.getBoardCd());
			File path = new File(webPath);
			if(!path.exists()) {
				continue;
			}
			File[] files = path.listFiles();
			List<File> fileList = Arrays.asList(files);
			if(!list.isEmpty() && !fileList.isEmpty()) {
				for(File f : fileList) {
					String fileName = f.getName();
					fileName 
					= "/resources/images/board/"+type.getBoardCd()+"/"+fileName;
					
					if(!list.contains(fileName)) {
						log.debug(fileName+"삭제");
						f.delete();
					}
					
				}
			}
			
		}
		
	}
	
	
	
}




