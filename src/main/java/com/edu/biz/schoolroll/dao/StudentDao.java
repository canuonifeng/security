package com.edu.biz.schoolroll.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;

public interface StudentDao extends BaseDao<Student> {

	Student findTopByClassroomIdOrderByNoDesc(Long classroomId);
}
