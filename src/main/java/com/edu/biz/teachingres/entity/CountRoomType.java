package com.edu.biz.teachingres.entity;

public class CountRoomType {
	private RoomType roomType;
	private long count;
	
	public CountRoomType(RoomType roomType, long count) {
		this.roomType = roomType;
		this.count = count;
	}
	
	public String getRoomTypeName() {
		return roomType.getName();
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public long getCount() {
		return count;
	}
}
