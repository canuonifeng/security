package com.edu.biz.teachingres.service.impl;

import java.util.List;
import java.util.Map;

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
	public BuildingRoom getBuildingRoom(Long id) {
		return buildingRoomDao.findOne(id);
	}

	@Override
	public Page<BuildingRoom> searchBuildingRooms(Map<String, Object> conditions, Pageable pageable) {
		return buildingRoomDao.findAll(new BuildingRoomSpecification(conditions), pageable);
	}

}
