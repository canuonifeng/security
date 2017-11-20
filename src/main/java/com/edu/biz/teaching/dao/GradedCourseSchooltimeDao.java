package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.GradedCourseSchooltime;

public interface GradedCourseSchooltimeDao extends BaseDao<GradedCourseSchooltime> {
	public void deleteByGradedCourseId(Long id);
}
