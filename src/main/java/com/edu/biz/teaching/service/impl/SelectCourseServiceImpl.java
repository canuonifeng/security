package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
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
import com.edu.biz.teaching.entity.SelectCourseClassAndClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.SelectCourseService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.SelectCourseClassSchooltimeSpecification;
import com.edu.biz.teaching.specification.SelectCourseClassSpecification;
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
	
	@Override
	public List<SelectCourse> findSelectCourses(Map<String, Object> conditions) {
		return selectCourseDao.findAll(new SelectCourseSpecification(conditions));
	}
	
	@Override
	public SelectCourse createSelectCourse(SelectCourse selectCourse) {
		Term term = termService.getTermByCurrent(1);
		selectCourse.setTermCode(term.getCode());
		return selectCourseDao.save(selectCourse);
	}
	
	@Override
	public void createSchooltimes(List<SelectCourseSchooltime> list) {
		for (SelectCourseSchooltime time : list) {
			selectCourseSchooltimeDao.save(time);
		}
	}
	
	@Override
	public void saveClass(List<SelectCourseClassAndClassSchooltime> list) {
		for(SelectCourseClassAndClassSchooltime selectCourseClassAndClassCourse: list) {
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
	public  List<SelectCourseClassAndClassSchooltime> findClass(Long id) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("selectCourseId", id);
		List<SelectCourseClassAndClassSchooltime> list = new ArrayList<>();
		List<SelectCourseClass> selectCourseClasses = selectCourseClassDao.findAll(new SelectCourseClassSpecification(conditions));
		for (SelectCourseClass selectCourseClass : selectCourseClasses) {
			SelectCourseClassAndClassSchooltime classAndClassSchooltime = new SelectCourseClassAndClassSchooltime();
			classAndClassSchooltime.setSelectCourseClass(selectCourseClass);
			Map<String, Object> map = new HashMap<>();
			map.put("selectCourseClassId", selectCourseClass.getId());
			List<SelectCourseClassSchooltime> times = selectCourseClassSchooltimeDao
					.findAll(new SelectCourseClassSchooltimeSpecification(map));
			classAndClassSchooltime.setSelectCourseClassSchooltimes(times);;
			list.add(classAndClassSchooltime);
		}
		return list;
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
		Map<String, Object> map = new HashMap<>();
		map.put("courseId", conditions.get("courseId"));
		map.put("termCode", term.getCode());
		List<SelectCourse> selectCourses = findSelectCourses(map);

		List<Classroom> notClassrooms = new ArrayList<>();
		if (selectCourses.size() != 0) {
			for (SelectCourse selectCourse : selectCourses) {
				if(conditions.containsKey("selectCourseId") && selectCourse.getId().equals(conditions.get("selectCourseId"))){
					continue;
				}
				List<Classroom> classrooms = selectCourse.getClassrooms();
				notClassrooms.addAll(classrooms);
			}
		}
		List<Long> notClassroomIds = new ArrayList<>();
		for (int i = 0; i < notClassrooms.size(); i++) {
			notClassroomIds.add(notClassrooms.get(i).getId());
		}
		conditions.put("selectCourseId", conditions.get("courseId"));
		conditions.put("currentTermCode", term.getCode());
		conditions.put("notClassroomIds", notClassroomIds);
		conditions.put("nature", "elective");
		return classroomService.findClassrooms(conditions);
	}
	
	@Transactional
	public void updateSelectCourseClass(Long id, List<SelectCourseClassAndClassSchooltime> list) {
		SelectCourse selectCourse = selectCourseDao.findOne(id);
		if (null == selectCourse) {
			throw new NotFoundException("该选课不存在");
		}
		selectCourseClassSchooltimeDao.deleteBySelectCourseClassSelectCourseId(id);
		selectCourseClassDao.deleteBySelectCourseId(id);
		saveClass(list);
	}
	
	@Override
	@Transactional
	public boolean deleteSelectCourse(Long id) {
		selectCourseClassSchooltimeDao.deleteBySelectCourseClassSelectCourseId(id);
		selectCourseSchooltimeDao.deleteBySelectCourseId(id);
		selectCourseClassDao.deleteBySelectCourseId(id);
		selectCourseDao.delete(id);
		return true;
	}
}
