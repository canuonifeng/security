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
	
	public Boolean deleteBuildingRoomByFloor(Long floor);
	
	public BuildingRoom getBuildingRoom(Long id);
	
	public List<CountRoomType> getRoomNum();
	
	public List<CountRoomType> getRoomNumByBuildingId(Long buildingId);
	
	public Page<BuildingRoom> searchBuildingRooms(Map<String, Object> conditions, Pageable pageable);
	
	public Long countBuildingRoom(Map<String, Object> conditions);
	
	public Long getFloorNum(Long id);
	
	public Map<String, List<BuildingRoom>> findBuildingRooms(Map<String, Object> conditions);
}
