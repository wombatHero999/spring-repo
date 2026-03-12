package com.kh.spring.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class BeforeTest {

	@Before("CommonPointcut.commonPoint()")
	public void beforeService(JoinPoint jp) {
		/* 
		 * JoinPoint
		 *  - AOP가 적용되는 메서드에 대한 정보를 제공하는 변수.
		 *  - 메서드가 적용되는 객체, 호출하는메서드 ,전달되는 매개인자 등 모든 정보에 접근 가능
		 *  - 모든 어드바이스 메서드의 첫번째 매개변수로 JoinPoint를 선언할 수 있다.
		 * */
		StringBuilder sb = new StringBuilder();
		sb.append("============================\n");
		
		// jp.getTarget() : aop가 적용된 실제 객체
		sb.append("start : "+jp.getTarget().getClass().getSimpleName()+"-");
		sb.append(jp.getSignature().getName()); // 메서드명
		sb.append("("+Arrays.toString(jp.getArgs())+")");
		
		log.debug(sb.toString());
	}
	
}


