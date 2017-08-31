package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.ProgramCourse;

public class ProgramCourseSpecification implements Specification<ProgramCourse> {
	private Map<String, Object> conditions;
	
	public ProgramCourseSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<ProgramCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (conditions.containsKey("programId")) {
			list.add(cb.equal(root.get("program").get("id").as(Long.class), this.conditions.get("programId")));
		}
		if (conditions.containsKey("courseId")) {
			list.add(cb.equal(root.get("course").get("id").as(Long.class), this.conditions.get("courseId")));
		}
		if (conditions.containsKey("weekPeriod")) {
			list.add(cb.equal(root.get("weekPeriod"), this.conditions.get("weekPeriod")));
		}
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
