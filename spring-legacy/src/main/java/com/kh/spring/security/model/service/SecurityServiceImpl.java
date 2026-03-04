package com.kh.spring.security.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.spring.security.model.dao.SecuirtyDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
	
	/*
	 * 사용자 인증 프로세스
	 * 1. 사용자가 입력한 아이디 값을 기반으로 사용자 정보 조회
	 * 2. 조회된 사용자 정보가 없으면 에러 throw
	 * 3. 인증 성공시 UserDetails객체 반환
	 * 4. 조회된 사용자 정보를 시큐리티의 매니저에게 넘겨 비밀번호 검사 및 권한 검증에 사용 
	 *  */	
	private final SecuirtyDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails member = dao.loadUserByUsername(username);
		
		if(member == null) {
			throw new UsernameNotFoundException(username);
		}
	
		return member;
	}

}




