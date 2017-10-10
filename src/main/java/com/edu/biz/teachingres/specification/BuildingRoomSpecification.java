package com.edu.biz.teachingres.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.edu.biz.teachingres.entity.BuildingRoom;

public class BuildingRoomSpecification implements Specification<BuildingRoom> {
	private Map<String, Object> conditions;
	
	public BuildingRoomSpecification(Map<String, Object> conditions) {
		this.conditions = conditions;
	}
	
	@Override
	public Predicate toPredicate(Root<BuildingRoom> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> list = new ArrayList<Predicate>();

		if (null != conditions) {
			if (conditions.containsKey("buildingId")) {
				list.add(cb.equal(root.get("building").get("id").as(Long.class), this.conditions.get("buildingId")));
			}
			if (conditions.containsKey("roomType")) {
				list.add(cb.equal(root.get("roomType").as(String.class), this.conditions.get("roomType")));
			}
			if (conditions.containsKey("name")) {
				list.add(cb.like(root.get("name"), "%" + this.conditions.get("roomType") + "%"));
			}
		}

		Predicate[] p = new Predicate[list.size()];
		return cb.and(list.toArray(p));
	}
}
