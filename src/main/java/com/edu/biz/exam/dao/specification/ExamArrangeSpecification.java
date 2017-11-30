package com.edu.biz.exam.dao.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.teaching.entity.ProgramCourse;

public class ExamArrangeSpecification implements Specification<ExamArrange> {
	private Map<String, Object> conditions;

	public ExamArrangeSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<ExamArrange> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("grade")) {
				list.add(cb.equal(root.get("grade"), this.conditions.get("grade")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("faculty").get("id"), this.conditions.get("facultyId")));
			}
			if (conditions.containsKey("termCode")) {
				list.add(cb.equal(root.get("termCode"), this.conditions.get("termCode")));
			}
			if (conditions.containsKey("programId")) {
				Subquery<ProgramCourse> subQuery = query.subquery(ProgramCourse.class);
				Root<ProgramCourse> programCourseRoot = subQuery.from(ProgramCourse.class);
				subQuery.select(programCourseRoot);
				subQuery.where(
						cb.equal(programCourseRoot.get("program").get("id"), this.conditions.get("programId")),
						cb.equal(programCourseRoot.get("testWay"), this.conditions.get("testWay")), 
						cb.equal(programCourseRoot.get("termCode"), this.conditions.get("termCode")),
						cb.equal(programCourseRoot.get("program").get("grade"), this.conditions.get("grade")),
						cb.equal(programCourseRoot.get("course").get("id"), root.get("course").get("id")));
				list.add(cb.exists(subQuery));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
