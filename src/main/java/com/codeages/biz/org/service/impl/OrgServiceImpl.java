package com.codeages.biz.org.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.codeages.biz.org.dao.OrgDao;
import com.codeages.biz.org.dao.specification.OrgSpecification;
import com.codeages.biz.org.entity.Organization;
import com.codeages.biz.org.service.OrgService;
import com.codeages.core.exception.NotFoundException;
import com.codeages.core.exception.ServiceException;
import com.codeages.util.BeanUtils;

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;
	
	@Override
	public Organization createOrg(Organization org) {
		this.filterParentOrg(org);
		if (!this.checkCode(org.getCode(), null)) {
			throw new ServiceException("406", "code已被占用");
		}
		org= orgDao.save(org);
		org = this.updateOrgCode(org, org.getParent());
		return org;
	}

	@Override
	@Validated
	public Organization updateOrg(Organization org) {
		this.filterParentOrg(org);
		Organization savedOrg = orgDao.findOne(org.getId());
		if (null == savedOrg) {
			throw new NotFoundException("组织不存在");
		}
		if (!this.checkCode(org.getCode(), org.getId())) {
			throw new ServiceException("406", "code已被占用");
		}
		BeanUtils.copyPropertiesWithCopyProperties(org, savedOrg, "name", "code", "faculty");
		org = orgDao.save(savedOrg);
		org = this.updateOrgCode(org, org.getParent());
		return org;
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

	private void filterParentOrg(Organization org) {
		if (null != org.getParent() && null == org.getParent().getId()) {
			org.setParent(null);
		}

		if (null != org.getParent() && null != org.getParent().getId()) {
			Organization parent = getOrg(org.getParent().getId());
			if (null == parent) {
				throw new NotFoundException("上级组织机构#" + org.getParent().getId() + "不存在");
			}
			org.setParent(parent);
		}
	}

	private Organization updateOrgCode(Organization org, Organization parentOrg) {
		String orgCode = "";
		if (null == parentOrg) {
			orgCode = org.getId() + ".";
		} else {
			orgCode = parentOrg.getOrgCode() + org.getId() + ".";
		}
		org.setOrgCode(orgCode);
		orgDao.save(org);
		return org;
	}
	@Override
	public List<Organization> findOrgs(Map<String, Object> conditions) {
		return orgDao.findAll(new OrgSpecification(conditions), new Sort(Direction.DESC, "createdTime"));
	}
}
