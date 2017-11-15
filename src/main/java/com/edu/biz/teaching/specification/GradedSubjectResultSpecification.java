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

import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;

public class GradedSubjectResultSpecification implements Specification<GradedSubjectResult> {
	private Map<String, Object> conditions;
	
	public GradedSubjectResultSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<GradedSubjectResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("grade")) {
				Join<GradedSubjectResult, GradedSubject> join = root.join("gradedSubject");
				list.add(cb.equal(join.get("grade"), conditions.get("grade")));
			}
			if (conditions.containsKey("studentId")) {
				list.add(cb.equal(root.get("student").get("id"), this.conditions.get("student")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
