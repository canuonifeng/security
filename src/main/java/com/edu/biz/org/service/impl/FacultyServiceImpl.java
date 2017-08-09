package com.edu.biz.org.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.org.dao.FacultyDao;
import com.edu.biz.org.dao.specification.FacultySpecification;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.FacultyStatus;
import com.edu.biz.org.service.FacultyService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class FacultyServiceImpl extends BaseService implements FacultyService {

	@Autowired
	private FacultyDao facultyDao;
	
	@Override
	public Faculty createFaculty(Faculty faculty) {
		if(!this.checkCode(faculty.getCode(), null)){
			throw new ServiceException("406","院系编码已被占用");
		}
		return facultyDao.save(faculty);
	}

	@Override
	public Faculty updateFaculty(Faculty faculty) {
		Faculty savedFaculty = facultyDao.findOne(faculty.getId());
		if (null == savedFaculty) {
			throw new NotFoundException("院系不存在");
		}
		if(!this.checkCode(faculty.getCode(), faculty.getId())) {
			throw new ServiceException("406","院系编码已被占用");
		}
		BeanUtils.copyPropertiesWithCopyProperties(faculty, savedFaculty, "name", "code");
		return facultyDao.save(savedFaculty);
	}
	
	@Override
	public Faculty changeFacultyStatus(Long id, FacultyStatus status) {
		Faculty savedFaculty = facultyDao.findOne(id);
		if (null == savedFaculty) {
			throw new NotFoundException("院系不存在");
		}
		savedFaculty.setStatus(status);
		return facultyDao.save(savedFaculty);
	}

	@Override
	public Boolean deleteFaculty(Long id) {
		facultyDao.delete(id);
		return null == facultyDao.findOne(id);
	}

	@Override
	public Faculty getFaculty(Long id) {
		return facultyDao.findOne(id);
	}

	@Override
	public Faculty getFacultyByCode(String code) {
		return facultyDao.getByCode(code);
	}
	
	public Boolean checkCode(String code, Long facultyId) {
		Faculty faculty = facultyDao.getByCode(code);
		if(null == faculty) {
			return true;
		}
		if(faculty.getId().equals(facultyId)) {
			return true;
		}
		return false;
	}

	@Override
	public Page<Faculty> searchFaculty(Map<String, Object> conditions, Pageable pageable) {
		return facultyDao.findAll(new FacultySpecification(conditions), pageable);
	}

}
