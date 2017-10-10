package com.edu.biz.teachingres.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.entity.BuildingRoom;

public class BuildingSpecification implements Specification<Building> {
	private Map<String, Object> conditions;
	
	public BuildingSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<Building> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("roomType")) {
				Join<Building, BuildingRoom> join = root.join("buildingRoom");
				list.add(cb.equal(join.get("roomType").as(String.class), conditions.get("roomType")));
			}
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%" + this.conditions.get("name") + "%"));
			}
			if (conditions.containsKey("code")) {
				list.add(cb.equal(root.get("code"), this.conditions.get("code")));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
