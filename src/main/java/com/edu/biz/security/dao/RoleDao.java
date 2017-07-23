package com.edu.biz.security.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.edu.biz.base.BaseDao;
import com.edu.biz.security.entity.Role;

@Repository
public interface RoleDao extends BaseDao<Role> {

	public List<Role> findByCodeIn(Set<String> codes);
}
