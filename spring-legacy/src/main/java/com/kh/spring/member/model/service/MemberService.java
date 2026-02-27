package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	Member loginMember(Member m);

	int insertMember(Member m);

	int idCheck(String userId);

	Member selectOne(String userId);

}
