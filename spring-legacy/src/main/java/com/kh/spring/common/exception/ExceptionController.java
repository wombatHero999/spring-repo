package com.kh.spring.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler
	public String exceptionHandler(Exception e, Model model) {
		e.printStackTrace();
		model.addAttribute("errorMsg", "서비스 이용중 문제가 발생했습니다.");
		return "common/errorPage";
	}
}



