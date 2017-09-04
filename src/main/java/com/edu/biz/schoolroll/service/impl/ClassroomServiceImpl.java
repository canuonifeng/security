package com.edu.biz.schoolroll.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.dao.ClassroomDao;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.specification.ClassroomSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class ClassroomServiceImpl extends BaseService implements ClassroomService {
	@Autowired
	private ClassroomDao classroomDao;
	
	@Override
	public Classroom createClassroom(Classroom classroom) {
		return classroomDao.save(classroom);
	}

	@Override
	public Boolean deleteClassroom(Long id) {
		classroomDao.delete(id);
		return null == classroomDao.findOne(id);
	}

	@Override
	public Classroom getClassroom(Long id) {
		return classroomDao.findOne(id);
	}

	@Override
	public Page<Classroom> searchClassroom(Map<String, Object> conditions, Pageable pageable) {
		return classroomDao.findAll(new ClassroomSpecification(conditions), pageable);
	}
	
	@Override
	public List<Classroom> findClassrooms(Map<String, Object> conditions) {
		return classroomDao.findAll(new ClassroomSpecification(conditions));
	}

	@Override
	public Classroom updateClassroom(Classroom classroom) {
		Classroom savedClassroom = classroomDao.findOne(classroom.getId());
		if (null == savedClassroom) {
			throw new NotFoundException("班级不存在");
		}
		if(!this.checkCode(classroom.getCode(), classroom.getId())) {
			throw new ServiceException("406","班级编码已被占用");
		}
		BeanUtils.copyPropertiesWithCopyProperties(classroom, savedClassroom, "name", "code", "isAssignNum");
		return classroomDao.save(savedClassroom);
	}

	public Boolean checkCode(String code, Long calssroomId) {
		Classroom classroom = classroomDao.getByCode(code);
		if(null == classroom) {
			return true;
		}
		if(classroom.getId().equals(calssroomId)) {
			return true;
		}
		return false;
	}
	@Override
	public Classroom getClassroomByCode(String code) {
		return classroomDao.getByCode(code);
	}
	@Override
	public Long countClassroom(Map<String, Object> conditions) {
		return classroomDao.count(new ClassroomSpecification(conditions));
	}
}
