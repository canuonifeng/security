package com.edu.biz.strategy.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.schoolroll.dao.StudentChangeDao;
import com.edu.biz.schoolroll.dao.StudentChangeLogDao;
import com.edu.biz.schoolroll.dao.StudentDao;
import com.edu.biz.schoolroll.entity.ChangeStatus;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentChangeLog;
import com.edu.biz.security.entity.User;

public class ChangeClassroom implements StudentChangeStrategy {
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private StudentChangeDao studentChangeDao;
	@Autowired
	private StudentChangeLogDao studentChangeLogDao;
	
	@Override
	@Transactional
	public void audit(StudentChangeLog log) {
		User currenUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//保存审核记录表
		log.setOpUser(currenUser);
		studentChangeLogDao.save(log);
		//更新异动表
		StudentChange change = log.getChange();
		change.setStatus(log.getNewStatus());
		change.setRefuseCause(log.getCause());
		change.setAuditUser(currenUser);
		studentChangeDao.save(change);
		//更新学籍表学员信息
		if (log.getNewStatus().equals(ChangeStatus.approved)) {
			Student student = change.getStudent();
			student.setClassroom(change.getNewClassroom());
			student.setNo(null);
			studentDao.save(student);
		}
	}
}
