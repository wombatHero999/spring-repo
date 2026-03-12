package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AroundTest {

	@Around("CommonPointcut.commonPoint()")
	public Object checkRunningTime(ProceedingJoinPoint jp) throws Throwable{
		long startTime = System.currentTimeMillis();
		// 타멧 메서드 실행 후 결과값 반환
		Object obj = jp.proceed();
		long endTime = System.currentTimeMillis();
		
		log.debug("Running Time : {} ms", (endTime - startTime));
		return obj;
	}
	
	
	
	
	
	
	
	
	
	
}

