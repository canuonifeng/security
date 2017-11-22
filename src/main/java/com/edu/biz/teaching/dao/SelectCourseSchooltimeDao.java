package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;

public interface SelectCourseSchooltimeDao extends BaseDao<SelectCourseSchooltime> {
	public void deleteBySelectCourseId(Long id);
}
