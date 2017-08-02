package com.edu.biz.security.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edu.biz.security.dao.OrgDao;
import com.edu.biz.security.dao.specification.OrgSpecification;
import com.edu.biz.security.entity.Organization;
import com.edu.biz.security.service.OrgService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;

	@Override
	public Organization createOrg(Organization org) {
		if (!this.checkCode(org.getCode(), null)) {
			throw new ServiceException("406", "code已被占用");
		}
		return orgDao.save(org);
	}

	@Override
	@Validated
	public Organization updateOrg(Organization org) {
		Organization savedOrg = orgDao.findOne(org.getId());
		if (null == savedOrg) {
			throw new NotFoundException("组织不存在");
		}
		if (!this.checkCode(org.getCode(), org.getId())) {
			throw new ServiceException("406", "code已被占用");
		}
		BeanUtils.copyPropertiesWithCopyProperties(org, savedOrg, "name", "code", "faculty", "parent");
		return orgDao.save(savedOrg);
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

	@Override
	public Boolean checkCode(String code, Long id) {
		Organization org = orgDao.getByCode(code);
		if (null == org) {
			return true;
		}
		if (org.getId().equals(id)) {
			return true;
		}
		return false;
	}

	private void recursive(Organization org, String status) {
		if(org.getChildren()!= null && !org.getChildren().equals("")) {
			
		}
	}
}
