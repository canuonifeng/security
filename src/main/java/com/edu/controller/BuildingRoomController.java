package com.edu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.CountRoomType;
import com.edu.biz.teachingres.entity.RoomType;
import com.edu.biz.teachingres.service.BuildingRoomService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/buildingroom")
@Api("教室")
public class BuildingRoomController extends BaseController<BuildingRoom> {
	@Autowired
	private BuildingRoomService buildingRoomService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('buildingRoom', 'add')")
	public BuildingRoom add(@RequestBody BuildingRoom buildingRoom) {
		return buildingRoomService.createBuildingRoom(buildingRoom);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('buildingRoom', 'edit')")
	public BuildingRoom edit(@PathVariable Long id, @RequestBody BuildingRoom buildingRoom) {
		buildingRoom.setId(id);
		return buildingRoomService.updateBuildingRoom(buildingRoom);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('buildingRoom', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return buildingRoomService.deleteBuildingRoom(id);
	}
	
	@RequestMapping(path = "/floor/{floor}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('buildingRoom', 'delete')")
	public void deleteFloor(@PathVariable Long floor) {
		buildingRoomService.deleteBuildingRoomByFloor(floor);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('buildingRoom', 'get')")
	public BuildingRoom get(@PathVariable Long id) {
		BuildingRoom buildingRoom = new BuildingRoom();
		buildingRoom.setId(id);
		return buildingRoomService.getBuildingRoom(
				buildingRoom.getId());
	}
	
	@RequestMapping(path = "/all",method = RequestMethod.GET)
	@PreAuthorize("hasPermission('buildingRoom', 'get')")
	public Map<String, List<BuildingRoom>> findAllBuildingRoom(@RequestParam Map<String, Object> conditions) {
		
		return buildingRoomService.findBuildingRooms(conditions);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('buildingRoom', 'get')")
	public Page<BuildingRoom> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return buildingRoomService.searchBuildingRooms(conditions, pageable);
	}
	
	@RequestMapping(path = "/numofroomtype",method = RequestMethod.GET)
	public List<CountRoomType> getRoomNum() {
		List<CountRoomType> list = new ArrayList<CountRoomType>();
		list = buildingRoomService.getRoomNum();
		for (RoomType type : RoomType.values()) {
			boolean isHas = false;
			for (CountRoomType roomType : list) {
				if(type.equals(roomType.getRoomType())) {
					isHas = true;
					break;
				}
			}
			if(!isHas) {
				CountRoomType countRoomType = new CountRoomType(type, 0);
				list.add(countRoomType);
			}
		}
		return list;
	}
}
