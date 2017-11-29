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

import com.edu.biz.teaching.entity.SelectCourse;
import com.edu.biz.teaching.entity.SelectCourseClass;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;

public class SelectCourseSchooltimeSpecification implements Specification<SelectCourseSchooltime> {
	private Map<String, Object> conditions;
	
	public SelectCourseSchooltimeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<SelectCourseSchooltime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("timeSlot")) {
				list.add(cb.equal(root.get("timeSlot"), this.conditions.get("timeSlot")));
			}
			if (conditions.containsKey("week")) {
				list.add(cb.equal(root.get("week"), this.conditions.get("week")));
			}
			if (conditions.containsKey("period")) {
				list.add(cb.equal(root.get("period"), this.conditions.get("period")));
			}
			if (conditions.containsKey("selectCourseId")) {
				list.add(cb.equal(root.get("selectCourse").get("id"), this.conditions.get("selectCourseId")));
			}
			if (conditions.containsKey("teacherId")) {
				Join<SelectCourseSchooltime, SelectCourse> join = root.join("selectCourse");
				list.add(cb.equal(join.get("termCode"), this.conditions.get("termCode")));
				Join<SelectCourse, SelectCourseClass> joinSelectCourseClass = join.join("selectCourseClasses");
				list.add(cb.equal(joinSelectCourseClass.get("teacher").get("id"), this.conditions.get("teacherId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
