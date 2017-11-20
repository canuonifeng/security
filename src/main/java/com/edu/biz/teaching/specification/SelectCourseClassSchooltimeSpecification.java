package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;

public class SelectCourseClassSchooltimeSpecification implements Specification<SelectCourseClassSchooltime> {
	private Map<String, Object> conditions;
	
	public SelectCourseClassSchooltimeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<SelectCourseClassSchooltime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("selectCourseClassId")) {
				list.add(cb.equal(root.get("selectCourseClass").get("id"), this.conditions.get("selectCourseClassId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
