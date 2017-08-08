package com.edu.biz.schoolroll.service;

import java.util.Map;

import com.edu.biz.schoolroll.entity.Member;

public interface MemberService {
	
	public Member createMember(Member member);
	
	public Member updateMember(Member member);
	
	public Boolean deleteMember(Long id);
	
	public Member getMember(Long id);

	public Long countMember(Map<String, Object> conditions);
}
