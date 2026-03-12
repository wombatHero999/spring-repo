package com.kh.spring.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
/* 
 * Aspect
 *  - 공통 관심사를 모듈화 한 클래스를 의미.
 *  - 이 클래스 안에는 실제로 수행될 공통 로직(Advice)그 로직을 적용시킬
 *  지점(Pointcut)을 정의해야 한다.
 * */
public class Aop {
	/* 
	 * JoinPoint
	 *  - 클라이언트가 호출 가능한 모든 메서드 실행 지점을 의미
	 *  - AOP가 적용될수 있는 후보지들.
	 * 
	 * Pointcut
	 *  - JoinPoint중에서 실제 Advice가 실행될 지점
	 * 
	 * @Pointcut("execution([접근제한자] [반환형] 패키지명.클래스명.메서드명([매개변수]))")
	 *  - * : 모든 값
	 *  - .. : 하위패키지 포함, 매개변수에서는 0개 이상을 의미.
	 * */
	
	//board패키지 아래 Impl로 끝나는 클래스의 모든 메서드를 Pointcut으로 지정
	@Pointcut("execution(* com.kh.spring.board..*Impl.*(..))")
	public void testPointcut() {}
	
	//@Before("testPointcut()")
	public void start() {
		// 서비스 로직 시작시 로깅
		log.debug("============= service start ==============");
	}
	
	
	
	
	

}







