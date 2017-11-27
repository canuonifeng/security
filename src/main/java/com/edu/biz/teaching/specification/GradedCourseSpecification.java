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

import com.edu.biz.teaching.entity.GradedCourse;
import com.edu.biz.teaching.entity.GradedTeaching;

public class GradedCourseSpecification implements Specification<GradedCourse> {
	private Map<String, Object> conditions;

	public GradedCourseSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<GradedCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("gradedId")) {
				list.add(cb.equal(root.get("gradedTeaching").get("id").as(Long.class), conditions.get("gradedId")));
			}
			if (conditions.containsKey("rankId")) {
				list.add(cb.equal(root.get("gradedRank").get("id").as(Long.class), conditions.get("rankId")));
			}
			if (conditions.containsKey("teacherId")) {
				list.add(cb.equal(root.get("teacher").get("id").as(Long.class), conditions.get("teacherId")));
			}
			if (conditions.containsKey("studentNumber")) {
				list.add(cb.equal(root.get("studentNumber").as(String.class), conditions.get("studentNumber")));
			}
			if (conditions.containsKey("termCode")) {
				list.add(cb.equal(root.get("gradedTeaching").get("termCode").as(Long.class), conditions.get("termCode")));
			}
			if (conditions.containsKey("gradedIds")) {
				List<Long> ids = (List<Long>) this.conditions.get("gradedIds");
				if(ids.size()>0) {
					Join<GradedCourse, GradedTeaching> join = root.join("gradedTeaching");
					list.add(join.get("id").in(ids.toArray()));
				}
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
