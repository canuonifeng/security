package com.edu.biz.security.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.security.entity.User;

public class UserSpecification implements Specification<User> {

	private Map<String, Object> conditions;

	public UserSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("username")) {
				list.add(cb.equal(root.get("username").as(String.class), this.conditions.get("username")));
			}
			if (conditions.containsKey("orgId")) {
				list.add(cb.equal(root.get("org").get("id").as(Long.class), this.conditions.get("orgId")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("faculty").get("id").as(Long.class), this.conditions.get("facultyId")));
			}
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name").as(String.class), "%"+this.conditions.get("name")+"%"));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}

}
