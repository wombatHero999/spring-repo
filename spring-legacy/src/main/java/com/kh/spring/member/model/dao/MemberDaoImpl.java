package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;
import com.kh.spring.security.model.vo.MemberExt;

@Repository
public class MemberDaoImpl implements MemberDao{
	
	@Autowired
	private SqlSessionTemplate session;
	
	@Override
	public Member loginMember(Member m) {
		return session.selectOne("member.loginMember" , m);
	}

	@Override
	public int insertMember(Member m) {
		return session.insert("member.insertMember" ,  m);
	}

	@Override
	public int idCheck(String userId) {
		return session.selectOne("member.idCheck", userId);
	}

	@Override
	public Member selectOne(String userId) {
		return session.selectOne("member.selectOne",userId);
	}

	@Override
	public int updateMember(MemberExt loginUser) {
		return session.update("member.updateMember", loginUser);
	}

	@Override
	public void insertAuthority(Member m) {
		session.insert("member.insertAuthority", m);
	}

}






