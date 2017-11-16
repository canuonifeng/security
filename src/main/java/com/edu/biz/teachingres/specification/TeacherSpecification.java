package com.edu.biz.teachingres.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teachingres.entity.Teacher;

public class TeacherSpecification implements Specification<Teacher> {
	private Map<String, Object> conditions;
	
	public TeacherSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%"+this.conditions.get("name")+"%"));
			}
			if (conditions.containsKey("no")) {
				list.add(cb.equal(root.get("no"), this.conditions.get("no")));
			}
			if (conditions.containsKey("status")) {
				list.add(cb.equal(root.get("status").as(String.class), this.conditions.get("status")));
			}
			if (conditions.containsKey("title")) {
				list.add(cb.equal(root.get("title").as(String.class), this.conditions.get("title")));
			}
			if (conditions.containsKey("degree")) {
				list.add(cb.equal(root.get("degree").as(String.class), this.conditions.get("degree")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("faculty").get("id").as(Long.class), this.conditions.get("facultyId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
