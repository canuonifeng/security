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
import com.edu.biz.teaching.entity.SelectCourseClassSchooltime;
import com.edu.biz.teaching.entity.SelectCourseSchooltime;

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
			if (conditions.containsKey("currentTermCode")) {
				Join<SelectCourseClassSchooltime, SelectCourseSchooltime> join = root.join("selectCourseSchooltime");
				Join<SelectCourseSchooltime, SelectCourse> joinSelectCourse = join.join("selectCourse");
				list.add(cb.equal(joinSelectCourse.get("termCode"), this.conditions.get("currentTermCode")));
				if(conditions.containsKey("week")){
					list.add(cb.equal(join.get("week"), this.conditions.get("week")));
				}
				if(conditions.containsKey("timeSlot")){
					list.add(cb.equal(join.get("timeSlot"), this.conditions.get("timeSlot")));
				}
				if(conditions.containsKey("period")){
					list.add(cb.equal(join.get("period"), this.conditions.get("period")));
				}
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
