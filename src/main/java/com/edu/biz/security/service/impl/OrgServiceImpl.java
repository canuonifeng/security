package com.edu.biz.security.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.security.dao.OrgDao;
import com.edu.biz.security.dao.specification.OrgSpecification;
import com.edu.biz.security.entity.Organization;
import com.edu.biz.security.service.OrgService;

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;

	@Override
	public Organization createOrg(Organization org) {
		return orgDao.save(org);
	}

	@Override
	public Organization updateOrg(Organization org) {
		return orgDao.save(org);
	}

	@Override
	public boolean deleteOrg(Long id) {
		orgDao.delete(id);
		return null == orgDao.findOne(id);
	}

	@Override
	public Organization getOrg(Long id) {
		return orgDao.findOne(id);
	}

	@Override
	public Page<Organization> searchOrgs(Map<String, Object> conditions, Pageable pageable) {
		return orgDao.findAll(new OrgSpecification(conditions), pageable);
	}

}
