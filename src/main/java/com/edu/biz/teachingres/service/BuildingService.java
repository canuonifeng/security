package com.edu.biz.teachingres.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;

public interface BuildingService {
	
	public Building createBuilding(Building building);
	
	public Building updateBuilding(Building building);
	
	public Boolean deleteBuilding(Long id);
	
	public Building getBuilding(Long id);
	
	public Page<Building> searchBuildings(Map<String, Object> conditions, Pageable pageable);
	
	//buildingRoom
	public BuildingRoom createBuildingRoom(BuildingRoom buildingRoom);
	
	public BuildingRoom updateBuildingRoom(BuildingRoom buildingRoom);
	
	public Boolean deleteBuildingRoom(Long id);
	
	public void deleteBuildingRoomByFloor(Long buildingId, Integer floor);
	
	public BuildingRoom getBuildingRoom(Long id);
	
	public List<CountRoomType> getRoomNum();
	
	public List<CountRoomType> getRoomNumByBuildingId(Long buildingId);
	
	public Page<BuildingRoom> searchBuildingRooms(Map<String, Object> conditions, Pageable pageable);
	
	public Long countBuildingRoom(Map<String, Object> conditions);
	
	public Long getFloorNum(Long id);
	
	public Map<String, List<BuildingRoom>> findBuildingRooms(Map<String, Object> conditions);

	public List<Building> findBuildings(Map<String, Object> conditions);
}
