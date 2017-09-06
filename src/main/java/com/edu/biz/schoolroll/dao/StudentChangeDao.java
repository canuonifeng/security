package com.edu.biz.schoolroll.dao;

import java.util.List;

import com.edu.biz.base.BaseDao;
import com.edu.biz.schoolroll.entity.StudentChange;

public interface StudentChangeDao extends BaseDao<StudentChange> {
	public List<StudentChange> findByStudentId(Long id);
}
