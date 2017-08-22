package com.edu.biz.teachingres.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teachingres.entity.RoomType;

public interface RoomTypeService {
	
	public RoomType createRoomType(RoomType roomType);
	
	public RoomType updateRoomType(RoomType roomType);
	
	public Boolean deleteRoomType(Long id);
	
	public RoomType getRoomType(Long id);
	
	public List<RoomType> findRoomTypes(Map<String, Object> conditions);
}
