package com.edu.biz.security.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.mapping.Set;
import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.security.entity.Role;

public class RoleSpecification implements Specification<Role> {
	private Map<String, Object> conditions;

	public RoleSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.equal(root.get("name").as(String.class), this.conditions.get("name")));
			}

			if (conditions.containsKey("roleCodes")) {
				list.add(cb.in(root.get("name").as(Set.class).in(conditions.get("roleCodes"))));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
