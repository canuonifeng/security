package com.edu.biz.security.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.security.entity.Organization;

public class OrgSpecification implements Specification<Organization>{
	
	private Map<String, String> conditions;
	
	public OrgSpecification(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.equal(root.get("name").as(String.class), this.conditions.get("name")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
