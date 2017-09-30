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

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;

public class StudentSpecification implements Specification<Student> {
	
	private Map<String, Object> conditions;
	
	public StudentSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();
		
		if (null != conditions) {
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%"+this.conditions.get("name")+"%"));
			}
			if (conditions.containsKey("no")) {
				list.add(cb.equal(root.get("no"), this.conditions.get("no")));
			}
			if (this.conditions.containsKey("hasNotNo")) {
				list.add(cb.isNull(root.get("no")));
			}
			if (this.conditions.containsKey("hasClassroomId") && this.conditions.get("hasClassroomId").equals("0")) {
				list.add(cb.isNull(root.get("classroom").get("id")));
			}
			if (conditions.containsKey("yearMonth")) {
				list.add(cb.equal(root.get("admissionTime"), this.conditions.get("yearMonth")));
			}
			if (conditions.containsKey("facultyId")) {
				list.add(cb.equal(root.get("major").get("faculty").get("id"), this.conditions.get("facultyId")));
			}
			if (conditions.containsKey("majorId")) {
				list.add(cb.equal(root.get("major").get("id"), this.conditions.get("majorId")));
			}
			if (conditions.containsKey("studentStatus")) {
				list.add(cb.equal(root.get("status").as(String.class), this.conditions.get("studentStatus")));
			}
			if (conditions.containsKey("classroomId")) {
				list.add(cb.equal(root.get("classroom").get("id"), this.conditions.get("classroomId")));
			}
			if (conditions.containsKey("studentFrom")) {
				list.add(cb.equal(root.get("origin").as(String.class), this.conditions.get("studentFrom")));
			}
			if (conditions.containsKey("grade")) {
				list.add(cb.equal(root.get("grade"), this.conditions.get("grade")));
			}
			if (conditions.containsKey("gender")) {
				list.add(cb.equal(root.get("gender").as(String.class), this.conditions.get("gender")));
			}
			if (conditions.containsKey("notAudit")) {
				Join<Student, StudentChange> join = root.join("changes");
				String[] status = new String[]{"facultyApproving","schoolApproving"};
				list.add(cb.not(root.get("status").as(String.class).in(status)));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
