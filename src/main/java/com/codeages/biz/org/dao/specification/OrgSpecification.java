package com.codeages.biz.org.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.codeages.biz.org.entity.Organization;
import com.codeages.framework.base.AbstractSpecification;

public class OrgSpecification extends AbstractSpecification<Organization> {

	public OrgSpecification(Map<String, Object> conditions) {
		super(conditions);
	}

	@Override
	public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		list.add(cb.isNull(root.get("parent").get("id"))); 

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
