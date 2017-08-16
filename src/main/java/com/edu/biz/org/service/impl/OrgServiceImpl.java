package com.edu.biz.org.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edu.biz.org.dao.OrgDao;
import com.edu.biz.org.dao.specification.OrgSpecification;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.Organization;
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.org.service.OrgService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private FacultyService facultyService;

	@Override
	public Organization createOrg(Organization org) {
		this.filterParentOrg(org);
		this.filterFaculty(org);
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
		this.filterFaculty(org);
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
	
	private void filterFaculty(Organization org) {
		if (null != org.getFaculty() && null == org.getFaculty().getId()) {
			org.setFaculty(null);
		}
		
		if (null != org.getFaculty() && null != org.getFaculty().getId()) {
			Faculty faculty = facultyService.getFaculty(org.getFaculty().getId());
			if (null == faculty) {
				throw new NotFoundException("院系"+org.getFaculty().getId()+"不存在") ;
			}
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
}
