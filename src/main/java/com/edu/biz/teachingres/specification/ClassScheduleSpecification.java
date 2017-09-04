package com.edu.biz.teachingres.specification;

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

public class ClassScheduleSpecification implements Specification<ClassSchedule> {
	private Map<String, Object> conditions;

	public ClassScheduleSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<ClassSchedule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("term")) {
				list.add(cb.equal(root.get("term").as(String.class), conditions.get("term")));
			}
			if (conditions.containsKey("courseId")) {
				list.add(cb.equal(root.get("course").get("id").as(Long.class), conditions.get("courseId")));
			}
			if (conditions.containsKey("classroomId")) {
				Join<ClassSchedule, Classroom> join = root.join("classrooms");
				list.add(cb.equal(join.get("id").as(Long.class), conditions.get("classroomId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
