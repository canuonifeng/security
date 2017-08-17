package com.edu.biz.teachingres.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teachingres.entity.Course;

public interface CourseDao extends BaseDao<Course> {

	Course getByCode(String code);

}
