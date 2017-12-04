package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.FinalGradePartCourse;

public class FinalGradePartCourseSpecification implements Specification<FinalGradePartCourse> {
	private Map<String, Object> conditions;

	public FinalGradePartCourseSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<FinalGradePartCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("termCode")) {
				list.add(cb.equal(root.get("termCode").as(String.class), conditions.get("termCode")));
			}
			if (conditions.containsKey("courseId")) {
				list.add(cb.equal(root.get("course").get("id").as(Long.class), conditions.get("courseId")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("faculty").get("id").as(Long.class), conditions.get("facultyId")));
			}
			if (conditions.containsKey("finalGradePartId")) {
				list.add(cb.equal(root.get("finalGradePart").get("id").as(Long.class),
						conditions.get("finalGradePartId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
