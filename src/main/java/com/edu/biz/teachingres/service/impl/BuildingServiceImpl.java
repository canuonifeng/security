package com.edu.biz.teachingres.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.base.BaseService;
import com.edu.biz.teachingres.dao.BuildingDao;
import com.edu.biz.teachingres.dao.BuildingRoomDao;
import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.biz.teachingres.specification.BuildingRoomSpecification;
import com.edu.biz.teachingres.specification.BuildingSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class BuildingServiceImpl extends BaseService implements BuildingService {
	@Autowired
	private BuildingDao buildingDao;

	@Autowired
	private BuildingRoomDao buildingRoomDao;

	@Override
	public Building createBuilding(Building building) {
		return buildingDao.save(building);
	}

	@Override
	public Building updateBuilding(Building building) {
		Building saveBuilding = buildingDao.findOne(building.getId());
		if (null == saveBuilding) {
			throw new NotFoundException("该建筑不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(building, saveBuilding, "name", "code");

		return buildingDao.save(saveBuilding);
	}

	@Override
	@Transactional
	public Boolean deleteBuilding(Long id) {
		buildingDao.delete(id);
		return true;
	}

	@Override
	public Building getBuilding(Long id) {
		return buildingDao.findOne(id);
	}

	@Override
	public Page<Building> searchBuildings(Map<String, Object> conditions, Pageable pageable) {
		return buildingDao.findAll(new BuildingSpecification(conditions), pageable);
	}

	@Override
	public BuildingRoom createBuildingRoom(BuildingRoom buildingRoom) {
		return buildingRoomDao.save(buildingRoom);
	}

	@Override
	public List<CountRoomType> getRoomNum() {
		return buildingRoomDao.countGroupByRoomType();
	}

	@Override
	public List<CountRoomType> getRoomNumByBuildingId(Long buildingId) {
		return buildingRoomDao.countGroupByRoomTypeByBuildingId(buildingId);
	}

	@Override
	public BuildingRoom updateBuildingRoom(BuildingRoom buildingRoom) {
		BuildingRoom saveBuildingRoom = buildingRoomDao.findOne(buildingRoom.getId());
		if (null == saveBuildingRoom) {
			throw new NotFoundException("该教室不存在");
		}
		BeanUtils.copyPropertiesWithIgnoreProperties(buildingRoom, saveBuildingRoom, "building");

		return buildingRoomDao.save(saveBuildingRoom);
	}

	@Override
	public Boolean deleteBuildingRoom(Long id) {
		buildingRoomDao.delete(id);
		return true;
	}

	@Override
	public BuildingRoom getBuildingRoom(Long id) {
		return buildingRoomDao.findOne(id);
	}

	@Override
	public Page<BuildingRoom> searchBuildingRooms(Map<String, Object> conditions, Pageable pageable) {
		return buildingRoomDao.findAll(new BuildingRoomSpecification(conditions), pageable);
	}

	@Override
	public Long countBuildingRoom(Map<String, Object> conditions) {
		return buildingRoomDao.count(new BuildingRoomSpecification(conditions));
	}

	@Override
	public List<Building> findBuildings(Map<String, Object> conditions) {
		return buildingDao.findAll(new BuildingSpecification(conditions));
	}

	@Override
	public List<BuildingRoom> findAllrooms(Map<String, Object> conditions) {
		return buildingRoomDao.findAll(new BuildingRoomSpecification(conditions));
	}

}
