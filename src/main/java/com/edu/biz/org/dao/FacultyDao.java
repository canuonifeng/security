package com.edu.biz.org.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.org.entity.Faculty;

public interface FacultyDao extends BaseDao<Faculty> {

	public Faculty getByCode(String code);
}
