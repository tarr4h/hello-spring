package com.kh.spring.member.model.service;

import com.kh.spring.member.vo.Member;

public interface MemberService {

	int insertMember(Member member);

	Member selectOneMember(String id);

}
