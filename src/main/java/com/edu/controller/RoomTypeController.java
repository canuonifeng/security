package com.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.RoomType;
import com.edu.biz.teachingres.service.BuildingRoomService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/roomtype")
@Api("教室类型")
public class RoomTypeController extends BaseController<RoomType> {
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
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('buildingRoom', 'get')")
	public BuildingRoom get(@PathVariable Long id) {
		BuildingRoom buildingRoom = new BuildingRoom();
		buildingRoom.setId(id);
		return buildingRoomService.getBuildingRoom(
				buildingRoom.getId());
	}
}
