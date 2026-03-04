package com.kh.spring.security.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/*
 * UserDetailsService
 *  - 스프링 시큐리티에서 인증 처리시 사용하는 인터페이스로, 사용자 정보를 조회하는 메서드인
 *    loadUserByUsername()을 정의하고 있다.
 *  
 *  loadUserByUsername()
 *   - 사용자의 "아이디"를 기반으로 UserDetails객체를 반환하는 메서드
 *   - 반환된 UserDetails는 비밀번호 및 권한 검사시 사용된다.
 *   - 일반적으론, DB에 저장된 사용자정보를 조회하는 방식으로 비지니스 로직을 작성한다.
 *  */
public interface SecurityService extends UserDetailsService {

}




