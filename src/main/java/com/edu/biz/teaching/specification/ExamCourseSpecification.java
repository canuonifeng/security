package com.edu.biz.teaching.specification;

import java.security.acl.Owner;
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

public class ExamCourseSpecification implements Specification<Course> {
	
	private Map<String, Object> conditions;

	public ExamCourseSpecification(Map<String, Object> conditions) {
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
			
			if (conditions.containsKey("facultyId")&&conditions.containsKey("termCode")) {
//				list.add(cb.equal(root.get("faculty").get("id"), this.conditions.get("facultyId")));
				Subquery<ProgramCourse> subQuery = query.subquery(ProgramCourse.class);
				Root<ProgramCourse> programCourseRoot = subQuery.from(ProgramCourse.class);
				subQuery.select(programCourseRoot);
				subQuery.where(cb.equal(programCourseRoot.get("termCode"), this.conditions.get("code")), cb.equal(programCourseRoot.get("faculty").get("id").as(Long.class), this.conditions.get("facultyId")));
				list.add(cb.exists(subQuery));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}

}
