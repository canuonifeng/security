package com.edu.biz.schoolroll.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.dao.StudentChangeDao;
import com.edu.biz.schoolroll.dao.StudentChangeLogDao;
import com.edu.biz.schoolroll.dao.StudentDao;
import com.edu.biz.schoolroll.entity.ChangeStatus;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentChangeLog;
import com.edu.biz.schoolroll.service.StudentChangeService;
import com.edu.biz.schoolroll.specification.StudentChangeSpecification;
import com.edu.biz.schoolroll.strategy.ContextStudentChangeFactory;
import com.edu.biz.schoolroll.strategy.StudentChangeStrategy;
import com.edu.biz.security.entity.User;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class StudentChangeServiceImpl extends BaseService implements StudentChangeService {
	@Autowired
	private StudentChangeDao studentChangeDao;
	@Autowired
	private StudentChangeLogDao studentChangeLogDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
    private Map<String, StudentChangeStrategy> strategyMap;
	
	@Override
	@Transactional
	public StudentChange createStudentChange(StudentChange studentChange) {
		return studentChangeDao.save(studentChange);
	}

	@Override
	public StudentChange updateStudentChange(StudentChange studentChange) {
		StudentChange saveStudentChange = studentChangeDao.findOne(studentChange.getId());
		if (null == saveStudentChange) {
			throw new NotFoundException("该学生不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(studentChange, saveStudentChange, "status", "refuseCause", "auditUser", "updatedTime");

		return studentChangeDao.save(studentChange);
	}

	@Override
	public Boolean deleteStudentChange(Long id) {
		studentChangeDao.delete(id);
		return null == studentChangeDao.findOne(id);
	}

	@Override
	public StudentChange getStudentChange(Long id) {
		return studentChangeDao.findOne(id);
	}
	
	@Override
	public List<StudentChange> findStudentChangeByStudentId(Long id) {
		return studentChangeDao.findByStudentId(id);
	}

	@Override
	public Page<StudentChange> searchStudentChanges(Map<String, Object> conditions, Pageable pageable) {
		return studentChangeDao.findAll(new StudentChangeSpecification(conditions), pageable);
	}
	
	@Override
	@Transactional
	public void audit(StudentChangeLog log) {
		User currenUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//保存审核记录表
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
			ContextStudentChangeFactory contextFactory = new ContextStudentChangeFactory(change, student);
			contextFactory.setStrategyMap(strategyMap);
			Student updateStudent = contextFactory.audit(log.getChange().getChangeType().toString());
			studentDao.save(updateStudent);
		}
	}
}
