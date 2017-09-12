package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.teaching.entity.Program;

public class ProgramSpecification implements Specification<Program> {
	private Map<String, Object> conditions;
	
	public ProgramSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Program> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("majorId")) {
				list.add(cb.equal(root.get("major").get("id"), this.conditions.get("majorId")));
			}
			if (conditions.containsKey("grade")) {
				list.add(cb.equal(root.get("grade"), this.conditions.get("grade")));
			}
			if (conditions.containsKey("facultyId")) {
				Join<Program, Major> join = root.join("major");
				list.add(cb.equal(join.get("faculty").get("id"), conditions.get("facultyId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
