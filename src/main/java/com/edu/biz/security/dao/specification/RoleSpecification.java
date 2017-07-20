package com.edu.biz.security.dao.specification;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.security.entity.Role;

public class RoleSpecification implements Specification<Role>{
	private Map<String, String> conditions;

	public RoleSpecification(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		// TODO Auto-generated method stub
		return null;
	}
}
