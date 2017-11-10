package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.Term;

public class TermSpecification implements Specification<Term> {
	private Map<String, Object> conditions;
	
	public TermSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Term> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("title")) {
				list.add(cb.like(root.get("title"), "%"+this.conditions.get("title")+"%"));
			}
			if (conditions.containsKey("current")) {
				list.add(cb.equal(root.get("current"), this.conditions.get("current")));
			}
			if (conditions.containsKey("inCodes")) {
				List<String> codes = (List<String>) this.conditions.get("inCodes");
//				root.get("id").in(ids.toArray());
				if(codes.size()>0) {
					list.add(root.get("code").in(codes.toArray()));
				}
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
