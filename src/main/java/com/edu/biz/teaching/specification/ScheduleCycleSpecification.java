package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.ScheduleTeacher;

public class ScheduleCycleSpecification implements Specification<ScheduleCycle> {
	private Map<String, Object> conditions;

	public ScheduleCycleSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<ScheduleCycle> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		
		if (null != conditions) {
			if (conditions.containsKey("scheduleId")) {
				list.add(cb.equal(root.get("classSchedule").get("id").as(Long.class), conditions.get("scheduleId")));
			}
			if (conditions.containsKey("period")) {
				list.add(cb.equal(root.get("period"), conditions.get("period")));
			}
			if (conditions.containsKey("week")) {
				list.add(cb.equal(root.get("week"), conditions.get("week")));
			}
			if (conditions.containsKey("buildingRoomId")) {
				list.add(cb.equal(root.get("buildingRoom").get("id"), conditions.get("buildingRoomId")));
			}
			if (conditions.containsKey("classroomId")) {
				Join<ScheduleCycle, ClassSchedule> joinSchedule = root.join("classSchedule");
				list.add(cb.equal(joinSchedule.get("id").as(Long.class), root.get("classSchedule").get("id")));
				list.add(cb.equal(joinSchedule.get("term"), conditions.get("termCode")));
				Join<ClassSchedule, Classroom> join = joinSchedule.join("classrooms");
				list.add(cb.equal(join.get("id").as(Long.class), conditions.get("classroomId")));
			}
			if (conditions.containsKey("teacherId")) {
				Join<ScheduleCycle, ClassSchedule> joinSchedule = root.join("classSchedule");
				list.add(cb.equal(joinSchedule.get("id").as(Long.class), root.get("classSchedule").get("id")));
				Join<ClassSchedule, ScheduleTeacher> join = joinSchedule.join("scheduleTeachers");
				list.add(cb.equal(join.get("teacher").get("id").as(Long.class), conditions.get("teacherId")));
				if(conditions.containsKey("master")) {
					list.add(cb.equal(join.get("master"), conditions.get("master")));
				}
			}
			if (conditions.containsKey("gradedCheckTeacherId")) {
				Join<ScheduleCycle, ClassSchedule> joinSchedule = root.join("classSchedule");
				list.add(cb.equal(joinSchedule.get("term"), conditions.get("currentTermCode")));
				Join<ClassSchedule, ScheduleTeacher> join = joinSchedule.join("scheduleTeachers");
				list.add(cb.equal(join.get("teacher").get("id").as(Long.class), conditions.get("gradedCheckTeacherId")));
				if(conditions.containsKey("master")) {
					list.add(cb.equal(join.get("master"), conditions.get("master")));
				}
			}
		}
		
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
