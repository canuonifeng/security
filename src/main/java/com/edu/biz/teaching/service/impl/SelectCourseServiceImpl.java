package com.edu.biz.teaching.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teaching.dao.SelectCourseClassDao;
import com.edu.biz.teaching.dao.SelectCourseClassSchooltimeDao;
import com.edu.biz.teaching.dao.SelectCourseDao;
import com.edu.biz.teaching.dao.SelectCourseSchooltimeDao;
import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClass;
import com.edu.biz.teaching.entity.SelectCourseClassAndClassCourse;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teaching.specification.SelectCourseClassSchooltimeSpecification;
import com.edu.biz.teaching.specification.SelectCourseSpecification;

@Service
public class SelectCourseServiceImpl extends BaseService implements SelectCourseService {
	@Autowired
	private SelectCourseDao selectCourseDao;
	@Autowired
	private SelectCourseSchooltimeDao selectCourseSchooltimeDao;
	@Autowired
	private SelectCourseClassDao selectCourseClassDao;
	@Autowired
	private SelectCourseClassSchooltimeDao selectCourseClassSchooltimeDao;
	
	public List<SelectCourse> findSelectCourses(Map<String, Object> conditions) {
		return selectCourseDao.findAll(new SelectCourseSpecification(conditions));
	}
	
	public SelectCourse createSelectCourse(SelectCourse selectCourse) {
		return selectCourseDao.save(selectCourse);
	}
	
	public void createSchooltimes(List<SelectCourseSchooltime> list) {
		for (SelectCourseSchooltime time : list) {
			selectCourseSchooltimeDao.save(time);
		}
	}
	
	public void saveClass(List<SelectCourseClassAndClassCourse> list) {
		for(SelectCourseClassAndClassCourse selectCourseClassAndClassCourse: list) {
			SelectCourseClass selectCourseClass = selectCourseClassDao.save(selectCourseClassAndClassCourse.getSelectCourseClass());
			Map<String, Object> map = new HashMap<>();
			map.put("selectCourseClassId", selectCourseClass.getId());
			List<SelectCourseClassSchooltime> times = findSchooltimesByClassId(map);
			if (!times.isEmpty()) {
				selectCourseClassSchooltimeDao.deleteBySelectCourseClassId(selectCourseClass.getId());
			}
			for (SelectCourseClassSchooltime classSchooltime: selectCourseClassAndClassCourse.getSelectCourseClassSchooltimes()) {
				classSchooltime.setSelectCourseClass(selectCourseClass);;
				selectCourseClassSchooltimeDao.save(classSchooltime);
			}
		}
	}
	
	@Override
	public List<SelectCourseClassSchooltime> findSchooltimesByClassId(Map<String, Object> conditions) {
		return selectCourseClassSchooltimeDao.findAll(new SelectCourseClassSchooltimeSpecification(conditions));
	}
}
