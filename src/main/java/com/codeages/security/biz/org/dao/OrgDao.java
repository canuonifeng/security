package com.codeages.security.biz.org.dao;


import com.codeages.framework.base.BaseDao;
import com.codeages.security.biz.org.entity.Organization;

public interface OrgDao extends BaseDao<Organization> {

	public Organization getByCode(String code);
}
