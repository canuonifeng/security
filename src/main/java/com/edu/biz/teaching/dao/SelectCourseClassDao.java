package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.SelectCourseClass;

public interface SelectCourseClassDao extends BaseDao<SelectCourseClass> {
	public void deleteBySelectCourseId(Long id);
}
