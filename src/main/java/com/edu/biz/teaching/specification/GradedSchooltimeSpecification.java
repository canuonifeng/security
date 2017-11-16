package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.GradedSchooltime;

public class GradedSchooltimeSpecification implements Specification<GradedSchooltime> {
	private Map<String, Object> conditions;
	
	public GradedSchooltimeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<GradedSchooltime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("gradedId")) {
				list.add(cb.equal(root.get("gradedTeaching").get("id"), this.conditions.get("gradedId")));
			}
			if (conditions.containsKey("period")) {
				list.add(cb.equal(root.get("period"), conditions.get("period")));
			}
			if (conditions.containsKey("timeSlot")) {
				list.add(cb.equal(root.get("timeSlot"), this.conditions.get("timeSlot")));
			}
			if (conditions.containsKey("week")) {
				list.add(cb.equal(root.get("week"), this.conditions.get("week")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
