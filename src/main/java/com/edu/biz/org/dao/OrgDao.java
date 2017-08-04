package com.edu.biz.org.dao;


import com.edu.biz.base.BaseDao;
import com.edu.biz.org.entity.Organization;

public interface OrgDao extends BaseDao<Organization> {

	public Organization getByCode(String code);
}
