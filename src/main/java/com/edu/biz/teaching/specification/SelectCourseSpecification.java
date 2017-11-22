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
import com.edu.biz.teaching.entity.SelectCourse;

public class SelectCourseSpecification implements Specification<SelectCourse> {
	private Map<String, Object> conditions;
	
	public SelectCourseSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<SelectCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("courseName")) {
				list.add(cb.equal(root.get("course").get("name"), this.conditions.get("courseName")));
			}
			if (conditions.containsKey("courseCode")) {
				list.add(cb.equal(root.get("course").get("code"), this.conditions.get("courseCode")));
			}
			if (conditions.containsKey("suitClassroom")) {
				Join<SelectCourse, Classroom> join = root.join("classrooms");
			    list.add(cb.like(join.get("name"), "%" + conditions.get("suitClassroom") + "%"));
			}
			if (conditions.containsKey("courseId")) {
				list.add(cb.equal(root.get("course").get("id"), this.conditions.get("courseId")));
			}
			if (conditions.containsKey("termCode")) {
				list.add(cb.equal(root.get("termCode"), this.conditions.get("termCode")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("course").get("faculty").get("id"), this.conditions.get("facultyId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}