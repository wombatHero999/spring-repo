package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;
import com.kh.spring.security.model.vo.MemberExt;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public Member loginMember(Member m) {
		return memberDao.loginMember(m);
	}

	@Override
	public int insertMember(Member m) {
		// 회원가입과 동시에 권한 추가
		int result = memberDao.insertMember(m);
		memberDao.insertAuthority(m);
		return result;
	}

	@Override
	public int idCheck(String userId) {
		return memberDao.idCheck(userId);
	}

	@Override
	public Member selectOne(String userId) {
		return memberDao.selectOne(userId);
	}

	@Override
	public int updateMember(MemberExt loginUser) {
		return memberDao.updateMember(loginUser);
	}
	

}








