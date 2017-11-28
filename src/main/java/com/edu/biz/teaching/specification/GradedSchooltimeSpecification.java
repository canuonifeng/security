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

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;

public class GradedSchooltimeSpecification implements Specification<GradedSchooltime> {
	private Map<String, Object> conditions;
	
	public GradedSchooltimeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<GradedSchooltime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("gradedId")) {
				list.add(cb.equal(root.get("gradedTeaching").get("id"), this.conditions.get("gradedId")));
			}
			if (conditions.containsKey("period")) {
				list.add(cb.equal(root.get("period"), conditions.get("period")));
			}
			if (conditions.containsKey("timeSlot")) {
				list.add(cb.equal(root.get("timeSlot"), this.conditions.get("timeSlot")));
			}
			if (conditions.containsKey("week")) {
				list.add(cb.equal(root.get("week"), this.conditions.get("week")));
			}
			if (conditions.containsKey("classroomId")) {
				Join<GradedSchooltime, GradedTeaching> join = root.join("gradedTeaching");
				Join<GradedTeaching, Classroom> joinClassroom = join.join("classrooms");
				list.add(cb.equal(joinClassroom.get("id").as(Long.class), this.conditions.get("classroomId")));
			}
			if (conditions.containsKey("checkGradedTeacherId")) {
				Join<GradedSchooltime, GradedTeaching> joinGradedTeaching = root.join("gradedTeaching");
				list.add(cb.equal(joinGradedTeaching.get("termCode"), this.conditions.get("currentTermCode")));
			}
			
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
