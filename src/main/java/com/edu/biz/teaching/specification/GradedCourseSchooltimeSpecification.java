package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.GradedCourseSchooltime;

public class GradedCourseSchooltimeSpecification implements Specification<GradedCourseSchooltime> {
	private Map<String, Object> conditions;
	
	public GradedCourseSchooltimeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<GradedCourseSchooltime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("gradedCourseId")) {
				list.add(cb.equal(root.get("gradedCourse").get("id"), this.conditions.get("gradedCourseId")));
			}
			if (conditions.containsKey("gradedId")) {
				list.add(cb.equal(root.get("gradedCourse").get("gradedTeaching").get("id"), this.conditions.get("gradedId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
