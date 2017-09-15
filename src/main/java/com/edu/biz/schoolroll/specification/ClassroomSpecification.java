package com.edu.biz.schoolroll.specification;

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
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.ProgramCourse;

public class ClassroomSpecification implements Specification<Classroom> {
	private Map<String, Object> conditions;
	
	public ClassroomSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Classroom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%"+this.conditions.get("name")+"%"));
			}
			if (conditions.containsKey("grade")) {
				list.add(cb.equal(root.get("grade").as(Long.class), this.conditions.get("grade")));
			}
			if (conditions.containsKey("majorId")) {
				list.add(cb.equal(root.get("major").get("id").as(Long.class), this.conditions.get("majorId")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("major").get("faculty").get("id"), this.conditions.get("facultyId")));
			}
			if (conditions.containsKey("isAssignNum")) {
				list.add(cb.equal(root.get("isAssignNum"), this.conditions.get("isAssignNum")));
			}
			if (conditions.containsKey("mergeCourseId")) {
				Join<Classroom, Program> joinProgram = root.join("program");
				Join<Program, ProgramCourse> join = joinProgram.join("programCourses");
				list.add(cb.equal(join.get("course").get("id").as(Long.class), conditions.get("mergeCourseId")));
				list.add(cb.equal(join.get("termCode"), conditions.get("termCode")));
			}
			if (conditions.containsKey("classroomIds")) {
				List<Long> ids = (List<Long>) this.conditions.get("classroomIds");
				if(ids.size()>0) {
					list.add(root.get("id").in(ids.toArray()));
				}
			}
		}
		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
