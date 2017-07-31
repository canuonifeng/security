package com.edu.biz.security.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.security.entity.Organization;

public interface OrgDao extends BaseDao<Organization> {

	public Organization getByCode(String code);
}
