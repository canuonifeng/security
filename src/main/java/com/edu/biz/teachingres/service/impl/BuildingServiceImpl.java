package com.edu.biz.teachingres.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		BeanUtils.copyPropertiesWithCopyProperties(building, saveBuilding, "name");

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
			throw new NotFoundException("该课程不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(buildingRoom, saveBuildingRoom, "floor", "name", "room_type", "seat_num");

		return buildingRoomDao.save(buildingRoom);
	}

	@Override
	public Boolean deleteBuildingRoom(Long id) {
		buildingRoomDao.delete(id);
		return true;
	}
	
	@Override
	@Transactional
	public void deleteBuildingRoomByFloor(Long buildingId, Integer floor) {
		buildingRoomDao.deleteByBuildingIdAndFloor(buildingId, floor);
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
	public Long getFloorNum(Long id) {
		return buildingRoomDao.countDistinctFloor(id);
	}
	
	@Override
	public Map<String, List<BuildingRoom>> findBuildingRooms(Map<String, Object> conditions) {
		List<BuildingRoom> buildingRooms = buildingRoomDao.findAll(new BuildingRoomSpecification(conditions));
		Map<String, List<BuildingRoom>> map = new TreeMap<>();
		for (BuildingRoom room : buildingRooms) {
			if (map.containsKey(room.getFloor().toString())) {
				map.get(room.getFloor().toString()).add(room);
			} else {
				List<BuildingRoom> list = new ArrayList<>();
				list.add(room);
				map.put(room.getFloor().toString(), list);
			}
		}
		
		return map;
	}

}
