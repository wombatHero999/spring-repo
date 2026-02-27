package com.kh.spring.member.model.dao;

import com.kh.spring.member.model.vo.Member;

public interface MemberDao {

	Member loginMember(Member m);

	int insertMember(Member m);

	int idCheck(String userId);

	Member selectOne(String userId);

}
