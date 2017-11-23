package com.edu.biz.teaching.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;

public interface SelectCourseClassSchooltimeDao extends BaseDao<SelectCourseClassSchooltime> {
	public void deleteBySelectCourseClassId(Long id);
	
	public void deleteBySelectCourseClassSelectCourseId(Long id);
}
