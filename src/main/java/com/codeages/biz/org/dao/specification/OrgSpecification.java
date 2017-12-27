package com.codeages.biz.org.dao.specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.codeages.biz.org.entity.Organization;

public class OrgSpecification implements Specification<Organization> {

	private Map<String, Object> conditions;

	public OrgSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		list.add(cb.isNull(root.get("parent").get("id"))); 

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
