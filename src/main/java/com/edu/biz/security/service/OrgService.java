package com.edu.biz.security.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.security.entity.Organization;

public interface OrgService {

	Organization createOrg(Organization org);

	Organization updateOrg(Organization org);

	boolean deleteOrg(Long id);

	Organization getOrg(Long id);

	Page<Organization> searchOrgs(Map<String, String> conditions, Pageable pageable);

}
