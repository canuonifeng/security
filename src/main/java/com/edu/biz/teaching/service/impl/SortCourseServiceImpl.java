package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.dao.ClassScheduleDao;
import com.edu.biz.teaching.dao.ScheduleClassroomDao;
import com.edu.biz.teaching.dao.ScheduleCycleDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleClassroom;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.service.SortCourseService;

@Service
public class SortCourseServiceImpl extends BaseService implements SortCourseService {
	@Autowired
	private ClassScheduleDao classScheduleDao;

	@Autowired
	private ScheduleCycleDao scheduleCycleDao;

	@Autowired
	private ScheduleClassroomDao scheduleClassroomDao;

	@Override
	public ClassSchedule createClassSchedule(ClassSchedule classSchedule) {
		return classScheduleDao.save(classSchedule);
	}

	public ClassSchedule getClassSchedule(String term, Long couresId, Long classroomId) {
		return classScheduleDao.findOne(new Specification<ClassSchedule>() {
			@Override
			public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("term").as(String.class), term));
				list.add(cb.equal(root.get("course").get("id").as(Long.class), couresId));
				Join<ClassSchedule, Classroom> join = root.join("classrooms");
				list.add(cb.equal(join.get("id").as(Long.class), classroomId));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		});
	}

	@Override
	public ScheduleCycle createScheduleCycle(ScheduleCycle scheduleCycle) {
		return scheduleCycleDao.save(scheduleCycle);
	}

	@Override
	public ScheduleClassroom createScheduleClassroom(ScheduleClassroom scheduleCalssroom) {
		return scheduleClassroomDao.save(scheduleCalssroom);
	}
}
