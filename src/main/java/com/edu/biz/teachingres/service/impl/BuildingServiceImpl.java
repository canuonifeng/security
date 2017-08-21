package com.edu.biz.teachingres.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teachingres.dao.BuildingDao;
import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.biz.teachingres.specification.BuildingSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class BuildingServiceImpl extends BaseService implements BuildingService {
	@Autowired
	private BuildingDao buildingDao;
	
	@Override
	public Building createBuilding(Building building) {
		return buildingDao.save(building);
	}

	@Override
	public Building updateBuilding(Building building) {
		Building saveBuilding = buildingDao.findOne(building.getId());
		if (null == saveBuilding) {
			throw new NotFoundException("该课程不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(building, saveBuilding, "name");

		return buildingDao.save(building);
	}

	@Override
	public Boolean deleteBuilding(Long id) {
		buildingDao.delete(id);
		return null == buildingDao.findOne(id);
	}

	@Override
	public Building getBuilding(Long id) {
		return buildingDao.findOne(id);
	}

	@Override
	public Page<Building> searchBuildings(Map<String, Object> conditions, Pageable pageable) {
		return buildingDao.findAll(new BuildingSpecification(conditions), pageable);
	}

}
