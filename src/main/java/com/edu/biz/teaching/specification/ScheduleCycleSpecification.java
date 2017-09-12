package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.ScheduleCycle;

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
		}
		
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
