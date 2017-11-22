package com.edu.biz.teaching.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.dao.SelectCourseClassDao;
import com.edu.biz.teaching.dao.SelectCourseClassSchooltimeDao;
import com.edu.biz.teaching.dao.SelectCourseDao;
import com.edu.biz.teaching.dao.SelectCourseSchooltimeDao;
import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClass;
import com.edu.biz.teaching.entity.SelectCourseClassAndClassCourse;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.SelectCourseClassSchooltimeSpecification;
import com.edu.biz.teaching.specification.SelectCourseSchooltimeSpecification;
import com.edu.biz.teaching.specification.SelectCourseSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

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
	@Autowired
	private TermService termService;
	@Autowired
	private ClassroomService classroomService;
	
	public List<SelectCourse> findSelectCourses(Map<String, Object> conditions) {
		return selectCourseDao.findAll(new SelectCourseSpecification(conditions));
	}
	
	public SelectCourse createSelectCourse(SelectCourse selectCourse) {
		Term term = termService.getTermByCurrent(1);
		selectCourse.setTermCode(term.getCode());
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
	
	@Override
	public SelectCourse getSelectCourse(Long id) {
		return selectCourseDao.findOne(id);
	}
	
	@Override
	public List<SelectCourseSchooltime> findTimes(Map<String, Object> conditions) {
		return selectCourseSchooltimeDao.findAll(new SelectCourseSchooltimeSpecification(conditions));
	}
	
	@Override
	@Transactional
	public void updateSchooltimes(Long id, List<SelectCourseSchooltime> list) {
		SelectCourse saveSelectCourse = selectCourseDao.findOne(id);
		if (null == saveSelectCourse) {
			throw new NotFoundException("该选课不存在");
		}
		selectCourseSchooltimeDao.deleteBySelectCourseId(id);
		createSchooltimes(list);
	}
	
	@Override
	public SelectCourse updateSelectCourse(SelectCourse selectCourse) {
		SelectCourse saveSelectCourse = selectCourseDao.findOne(selectCourse.getId());
		if (null == saveSelectCourse) {
			throw new NotFoundException("该选课不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(selectCourse, saveSelectCourse, "course", "schooltime", "classrooms");
		return selectCourseDao.save(saveSelectCourse);
	}
	
	@Override
	public List<Classroom> findSelectCourseClassrooms(Map<String, Object> conditions) {
		Term term = termService.getTermByCurrent(1);
		conditions.put("selectCourseId", conditions.get("courseId"));
		conditions.put("currentTermCode", term.getCode());
		conditions.put("facultyId", conditions.get("facultyId"));
		conditions.put("grade", conditions.get("grade"));
		conditions.put("nature", "elective");
		return classroomService.findClassrooms(conditions);
	}
}
