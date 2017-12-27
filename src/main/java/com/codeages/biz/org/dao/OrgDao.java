package com.codeages.biz.org.dao;


import com.codeages.biz.base.BaseDao;
import com.codeages.biz.org.entity.Organization;

public interface OrgDao extends BaseDao<Organization> {

	public Organization getByCode(String code);
}
