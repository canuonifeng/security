package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.GradedSchooltime;

public interface GradedSchooltimeDao extends BaseDao<GradedSchooltime> {
	
	public void deleteByGradedTeachingId(Long id);
}
