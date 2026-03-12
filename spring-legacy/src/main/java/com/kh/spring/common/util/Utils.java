package com.kh.spring.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

public class Utils {
	// 파일저장 함수
	// 파일을 저장하면서, 수정된 파일명을 반환
	public static String saveFile(
		MultipartFile upfile ,
		ServletContext application,
		String boardCd
	) {
		// 첨부파일을 저장할 저장경로 설정
		String webPath = 
				"/resources/images/board/"+boardCd+"/";
		
		// 실제 서버 파일시스템의 절대경로를 반환하는 메서드
		//  ex) C://springWorkspace/spring-legacy/...
		String serverPath = application
								.getRealPath(webPath);
		File dir = new File(serverPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		// 랜덤 파일명 생성
		//  현재시간 + 랜덤값
		
		String originName = upfile.getOriginalFilename();
		String currentTime 
		= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int random = new Random().nextInt(90000) + 10000;
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime+random+ext;
		
		try {
			//서버에 파일 업로드
			upfile.transferTo(new File(serverPath+changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return webPath+changeName;
	}
	/* 
	 * XSS(크로스 사이트 스크립트공격)
	 *  - 스크립트 삽입 공격
	 *  - 사용자가 script태그를 게시글에 작성하여 클라이언트가 게시글 클릭시 
	 *  script에 지정한 코드가 실행되도록 유도하는 방식
	 *  - 위 내용을 그대로 db에 저장 후 브라우저에 렌더링하면 문제가 발생할 수 있으므로
	 *  태그가 아닌 기본 문자열로 인식할 수 있게끔 html내부 entity로 변환처리를 수행해
	 *  야한다.
	 * */
	public static String XSSHandling(String content) {
		if(content != null) {
			content = content.replaceAll("&", "&amp;");
			content = content.replaceAll("<", "&lt;");
			content = content.replaceAll(">", "&gt;");
			content = content.replaceAll("\"", "&quot;");
		}
		return content;
	}
	
	// 개행문자 처리
	// textarea -> \n , p -> <br>
	public static String newLineHandling(String content) {
		return content.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	}
	
	// 개행해제 처리
	public static String newLineClear(String content) {
		return content.replaceAll("<br>","\n");
	}
	
	
	
	
	
}

