package com.kh.spring.security.model.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kh.spring.member.model.vo.Member;

public class MemberExt extends Member implements UserDetails {
	
	// SimpleGrantedAuthority
	//  - 문자열 형태의 권한
	//  - "ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER"...
	private List<SimpleGrantedAuthority> authorities;
	
	// 사용자가 가진 권한목록을 반환하는 메서드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	// getPassword / getUsername
	//  - 시큐리티에서 비밀번호/아이디를 가져올 때 사용하는 메서드
	//  - id역할의 필드가 username이 아니거나, 비밀번호역할의 필드가 password가
	//    아닌 경우 오버라이딩이 필요.
	@Override
	public String getPassword() {
		return getUserPwd();
	}

	@Override
	public String getUsername() {
		return getUserId();
	}

	// 계정의 만료상태, 잠금상태, 사용가능여부 등을 반환하는 메서드.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}







