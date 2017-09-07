package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;
import com.edu.biz.teachingres.entity.RoomType;
import com.edu.biz.teachingres.entity.pojo.BuildingVo;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/building")
@Api("建筑")
public class BuildingController extends BaseController<Object> {
	@Autowired
	private BuildingService buildingService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('building', 'add')")
	public Building addBuilding(@RequestBody Building building) {
		return buildingService.createBuilding(building);
	}
	@RequestMapping(path = "{id}/room",method = RequestMethod.POST)
	@PreAuthorize("hasPermission('building', 'add')")
	public BuildingRoom addBuildingRoom(@PathVariable Long id, @RequestBody BuildingRoom buildingRoom) {
		Building building = buildingService.getBuilding(id);
		buildingRoom.setBuilding(building);
		return buildingService.createBuildingRoom(buildingRoom);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('building', 'edit')")
	public Building editBuilding(@PathVariable Long id, @RequestBody Building building) {
		building.setId(id);
		return buildingService.updateBuilding(building);
	}
	
	@RequestMapping(path = "/{buildingId}/room/{roomId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('building', 'edit')")
	public BuildingRoom editBuildingRoom(@PathVariable Long buildingId, @PathVariable Long roomId, @RequestBody BuildingRoom buildingRoom) {
		buildingRoom.setId(roomId);
		Building building = buildingService.getBuilding(buildingId);
		buildingRoom.setBuilding(building);
		return buildingService.updateBuildingRoom(buildingRoom);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('building', 'delete')")
	public boolean deleteBuilding(@PathVariable Long id) {
		return buildingService.deleteBuilding(id);
	}
	
	@RequestMapping(path = "/{buildingId}/room/{roomId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('building', 'delete')")
	public boolean deleteBuildingRoom(@PathVariable Long roomId) {
		return buildingService.deleteBuildingRoom(roomId);
	}
	
	@RequestMapping(path = "/{buildingId}/room/floor/{floor}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('building', 'delete')")
	public void deleteFloor(@PathVariable Long buildingId, @PathVariable Integer floor) {
		buildingService.deleteBuildingRoomByFloor(buildingId, floor);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('building', 'get')")
	public Building getBuilding(@PathVariable Long id) {
		return buildingService.getBuilding(id);
	}

	@RequestMapping(path = "/room/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('building', 'get')")
	public BuildingRoom getBuildingRoom(@PathVariable Long id) {
		return buildingService.getBuildingRoom(id);
	}
	
	@RequestMapping(path = "/{id}/room/all",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('building', 'get')")
	public Map<String, List<BuildingRoom>> findAllBuildingRoom(@PathVariable Long id, @RequestParam Map<String, Object> conditions) {
		conditions.put("buildingId", id);
		return buildingService.findBuildingRooms(conditions);
	}
	
	@RequestMapping(path = "/building/all",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('building', 'get')")
	public List<Building> findBuildings(@PathVariable Long id, @RequestParam Map<String, Object> conditions) {
		return buildingService.findBuildings(conditions);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('building', 'get')")
	public Page<BuildingVo> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Building> page = buildingService.searchBuildings(conditions, pageable);
		List<BuildingVo> buildingVos = new ArrayList<BuildingVo>();
		for (Building building : page.getContent()) {
			BuildingVo buildingVo = new BuildingVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(building, buildingVo);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("buildingId", building.getId());
			Long buildingRoomNum = buildingService.countBuildingRoom(map);
			Long floorNum = buildingService.getFloorNum(building.getId());
			List<CountRoomType> roomType = buildingService.getRoomNumByBuildingId(building.getId());
			buildingVo.setRoomDetail(roomType);
			buildingVo.setFloorNum(floorNum.intValue());
			buildingVo.setClassroomNum(buildingRoomNum.intValue());
			buildingVos.add(buildingVo);
		}
		Page<BuildingVo> buildingVoPage = new PageImpl<>(buildingVos, pageable, page.getTotalElements());
		return buildingVoPage;
	}

	@RequestMapping(path = "/room/numoftype", method = RequestMethod.GET)
	public List<CountRoomType> getRoomNum() {
		List<CountRoomType> list = new ArrayList<CountRoomType>();
		list = buildingService.getRoomNum();
		for (RoomType type : RoomType.values()) {
			boolean isHas = false;
			for (CountRoomType roomType : list) {
				if (type.equals(roomType.getRoomType())) {
					isHas = true;
					break;
				}
			}
			if (!isHas) {
				CountRoomType countRoomType = new CountRoomType(type, 0);
				list.add(countRoomType);
			}
		}
		return list;
	}
}
