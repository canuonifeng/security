package com.edu.biz.teachingres.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teachingres.dao.TeacherDao;
import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.entity.TeacherStatus;
import com.edu.biz.teachingres.service.TeacherService;
import com.edu.biz.teachingres.specification.TeacherSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class TeacherServiceImpl extends BaseService implements TeacherService {
	@Autowired
	private TeacherDao teacherDao;
	
	@Override
	public Teacher createTeacher(Teacher teacher) {
		return teacherDao.save(teacher);
	}

	@Override
	public Teacher updateTeacher(Teacher teacher) {
		Teacher saveTeacher = teacherDao.findOne(teacher.getId());
		if (null == saveTeacher) {
			throw new NotFoundException("该教师不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(teacher, saveTeacher, "no", "name", "gender", "status", "start_work_time");

		return teacherDao.save(saveTeacher);
	}
	
	@Override
	public Teacher changeTeacherStatus(Long id, TeacherStatus status) {
		Teacher savedTeacher = teacherDao.findOne(id);
		if (null == savedTeacher) {
			throw new NotFoundException("该教师不存在");
		}
		savedTeacher.setStatus(status);
		return teacherDao.save(savedTeacher);
	}

	@Override
	public Boolean deleteTeacher(Long id) {
		teacherDao.delete(id);
		return null == teacherDao.findOne(id);
	}

	@Override
	public Teacher getTeacher(Long id) {
		return teacherDao.findOne(id);
	}

	@Override
	public Page<Teacher> searchTeachers(Map<String, Object> conditions, Pageable pageable) {
		return teacherDao.findAll(new TeacherSpecification(conditions), pageable);
	}

}
