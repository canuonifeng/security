package com.edu.biz.schoolroll.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.dao.MemberDao;
import com.edu.biz.schoolroll.entity.Member;
import com.edu.biz.schoolroll.service.MemberService;
import com.edu.biz.schoolroll.specification.MemberSpecification;
import com.edu.core.util.BeanUtils;

@Service
public class MemberServiceImpl extends BaseService implements MemberService {
	@Autowired
	private MemberDao memberDao;

	@Override
	public Member createMember(Member member) {
		return memberDao.save(member);
	}

	@Override
	public Member getMember(Long id) {
		return memberDao.findOne(id);
	}

	@Override
	public Boolean deleteMember(Long id) {
		memberDao.delete(id);
		return null == memberDao.findOne(id);
	}

	@Override
	public Member updateMember(Member member) {
		Member savedMember = memberDao.findOne(member.getId());
		BeanUtils.copyPropertiesWithCopyProperties(member, savedMember, "classroom", "student");
		return memberDao.save(savedMember);
	}

	@Override
	public Long countMember(Map<String, Object> conditions) {
		return memberDao.count(new MemberSpecification(conditions));
	}
}
