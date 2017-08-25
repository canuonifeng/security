package com.edu.biz.teachingres.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.teachingres.dao.BuildingRoomDao;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;
import com.edu.biz.teachingres.service.BuildingRoomService;
import com.edu.biz.teachingres.specification.BuildingRoomSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class BuildingRoomServiceImpl extends BaseService implements BuildingRoomService {
	@Autowired
	private BuildingRoomDao buildingRoomDao;
	
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
		return null == buildingRoomDao.findOne(id);
	}
	
	@Override
	public Boolean deleteBuildingRoomByFloor(Long floor) {
		buildingRoomDao.deleteByFloor(floor);
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("floor", floor);
		return null == buildingRoomDao.findAll(new BuildingRoomSpecification(conditions));
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
