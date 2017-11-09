package com.edu.biz.teachingres.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teachingres.entity.Course;

public class CourseSpecification implements Specification<Course> {
	private Map<String, Object> conditions;
	
	public CourseSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%"+this.conditions.get("name")+"%"));
			}
			if (conditions.containsKey("code")) {
				list.add(cb.equal(root.get("code"), this.conditions.get("code")));
			}
			if (conditions.containsKey("notCourseIds")) {
				List<Long> ids = (List<Long>) this.conditions.get("notCourseIds");
//				root.get("id").in(ids.toArray());
				if(ids.size()>0) {
					list.add(cb.not(root.get("id").in(ids.toArray())));
				}
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("faculty").get("id"), this.conditions.get("facultyId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
