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

import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.FinalGradePartStudent;

public class FinalGradePartStudentSpecification implements Specification<FinalGradePartStudent> {
	private Map<String, Object> conditions;

	public FinalGradePartStudentSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<FinalGradePartStudent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("studentId")) {
				list.add(cb.equal(root.get("student").get("id").as(String.class), conditions.get("studentId")));
			}
			if (conditions.containsKey("score")) {
				list.add(cb.equal(root.get("score"), conditions.get("score")));
			}
			if (conditions.containsKey("facultyId")) {
				Join<FinalGradePartStudent, FinalGradePartCourse> join = root.join("finalGradePartCourse");
				list.add(cb.equal(join.get("faculty").get("id"), this.conditions.get("facultyId")));
				if(conditions.containsKey("courseId")){
					list.add(cb.equal(join.get("course").get("id"), this.conditions.get("courseId")));
				}
				if(conditions.containsKey("termCode")){
					list.add(cb.equal(join.get("termCode"), this.conditions.get("termCode")));
				}
			}
			if(conditions.containsKey("finalGradePartCourseId")){
				list.add(cb.equal(root.get("finalGradePartCourse").get("id"), this.conditions.get("finalGradePartCourseId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
