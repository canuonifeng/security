package com.codeages.biz.security.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.codeages.base.BaseDao;
import com.codeages.biz.security.entity.Role;

@Repository
public interface RoleDao extends BaseDao<Role> {
	
	public Role getByCode(String code);
	
	public List<Role> findByCodeIn(Set<String> codes);
}
