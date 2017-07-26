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
import com.edu.biz.org.service.FacultyService;

@Service
public class FacultyServiceImpl extends BaseService implements FacultyService {

	@Autowired
	private FacultyDao facultyDao;
	
	@Override
	public Faculty createFaculty(Faculty faculty) {
		return facultyDao.save(faculty);
	}

	@Override
	public Faculty updateFaculty(Faculty faculty) {
		return facultyDao.save(faculty);
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

	@Override
	public Page<Faculty> searchFaculty(Map<String, Object> conditions, Pageable pageable) {
		return facultyDao.findAll(new FacultySpecification(conditions), pageable);
	}

}
