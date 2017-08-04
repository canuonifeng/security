package com.edu.biz.org.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.org.entity.Organization;

public interface OrgService {

	Organization createOrg(Organization org);

	Organization updateOrg(Organization org);

	boolean deleteOrg(Long id);

	Organization getOrg(Long id);

	Page<Organization> searchOrgs(Map<String, Object> conditions, Pageable pageable);

	public Boolean checkCode(String code,Long id);
}
