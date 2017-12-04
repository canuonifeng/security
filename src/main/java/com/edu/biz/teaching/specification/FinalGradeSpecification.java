package com.edu.biz.teaching.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teachingres.entity.Course;

public class FinalGradeSpecification implements Specification<Course> {
	private Map<String, Object> conditions;

	public FinalGradeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	@Override
	public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%" + this.conditions.get("name") + "%"));
			}

			if (conditions.containsKey("facultyId") && conditions.containsKey("termCode")) {
				Subquery<ProgramCourse> subQuery = query.subquery(ProgramCourse.class);
				Root<ProgramCourse> programCourseRoot = subQuery.from(ProgramCourse.class);
				subQuery.select(programCourseRoot);
				subQuery.where(
						cb.equal(programCourseRoot.get("program").get("major").get("faculty").get("id"),
								this.conditions.get("facultyId")),
						cb.equal(programCourseRoot.get("termCode"), this.conditions.get("termCode")),
						cb.equal(programCourseRoot.get("course").get("id"), root.get("id")));
				list.add(cb.exists(subQuery));
			}
			
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("faculty").get("id"), this.conditions.get("facultyId")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
