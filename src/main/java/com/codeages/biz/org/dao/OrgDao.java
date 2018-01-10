package com.codeages.biz.org.dao;


import com.codeages.biz.org.entity.Organization;
import com.codeages.framework.base.BaseDao;

public interface OrgDao extends BaseDao<Organization> {

	public Organization getByCode(String code);
}
