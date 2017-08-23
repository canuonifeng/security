package com.edu.biz.teachingres.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;

public interface BuildingRoomService {
	
	public BuildingRoom createBuildingRoom(BuildingRoom buildingRoom);
	
	public BuildingRoom updateBuildingRoom(BuildingRoom buildingRoom);
	
	public Boolean deleteBuildingRoom(Long id);
	
	public BuildingRoom getBuildingRoom(Long id);
	
	public List<CountRoomType> getRoomNum();
	
	public Page<BuildingRoom> searchBuildingRooms(Map<String, Object> conditions, Pageable pageable);
}
