package com.codeages.security.biz.security.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.mapping.Set;

import com.codeages.framework.base.AbstractSpecification;
import com.codeages.security.biz.security.entity.Role;

public class RoleSpecification extends AbstractSpecification<Role> {

	public RoleSpecification(Map<String, Object> conditions) {
		super(conditions);
	}

	@Override
	public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name").as(String.class), "%"+this.conditions.get("name")+"%"));
			}

			if (conditions.containsKey("roleCodes")) {
				list.add(cb.in(root.get("name").as(Set.class).in(conditions.get("roleCodes"))));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
