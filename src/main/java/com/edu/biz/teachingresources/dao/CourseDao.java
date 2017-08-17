package com.edu.biz.teachingresources.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.teachingresources.entity.Course;

public interface CourseDao extends BaseDao<Course> {

	Course getByCode(String code);

}
