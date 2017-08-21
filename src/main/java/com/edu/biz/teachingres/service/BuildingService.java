package com.edu.biz.teachingres.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teachingres.entity.Building;

public interface BuildingService {
	
	public Building createBuilding(Building building);
	
	public Building updateBuilding(Building building);
	
	public Boolean deleteBuilding(Long id);
	
	public Building getBuilding(Long id);
	
	public Page<Building> searchBuildings(Map<String, Object> conditions, Pageable pageable);
}
