package com.edu.biz.org.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.mapping.Set;
import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.org.entity.Faculty;

public class FacultySpecification implements Specification<Faculty> {
	private Map<String, Object> conditions;

	public FacultySpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<Faculty> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%"+this.conditions.get("name")+"%"));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
