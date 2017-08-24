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
import com.edu.biz.teachingres.entity.CountRoomType;
import com.edu.biz.teachingres.entity.pojo.BuildingVo;
import com.edu.biz.teachingres.service.BuildingRoomService;
import com.edu.biz.teachingres.service.BuildingService;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/building")
@Api("建筑")
public class BuildingController extends BaseController<Building> {
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private BuildingRoomService buildingRoomService;
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('building', 'add')")
	public Building add(@RequestBody Building building) {
		return buildingService.createBuilding(building);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('building', 'edit')")
	public Building edit(@PathVariable Long id, @RequestBody Building building) {
		building.setId(id);
		return buildingService.updateBuilding(building);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('building', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return buildingService.deleteBuilding(id);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('building', 'get')")
	public Building get(@PathVariable Long id) {
		Building Building = new Building();
		Building.setId(id);
		return buildingService.getBuilding(Building.getId());
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
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("buildingId", building.getId());
			Long buildingRoomNum = buildingRoomService.countBuildingRoom(map);
			Long floorNum = buildingRoomService.getFloorNum();
			List<CountRoomType> roomType = buildingRoomService.getRoomNumByBuildingId(building.getId());
			buildingVo.setRoomDetail(roomType);
			buildingVo.setFloorNum(floorNum.intValue());
			buildingVo.setClassroomNum(buildingRoomNum.intValue());
			buildingVos.add(buildingVo);
		}
		Page<BuildingVo> buildingVoPage = new PageImpl<>(buildingVos, pageable, page.getTotalElements());
		return buildingVoPage;
	}
}
