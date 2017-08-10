package com.edu.biz.schoolroll.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.schoolroll.entity.Classroom;

public interface ClassroomDao extends BaseDao<Classroom> {
	public Classroom getByCode(String code);
}
