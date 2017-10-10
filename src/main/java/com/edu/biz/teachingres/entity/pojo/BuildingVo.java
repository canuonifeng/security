package com.edu.biz.teachingres.entity.pojo;

import java.util.List;

import com.edu.biz.teachingres.entity.Building;
import com.edu.biz.teachingres.entity.CountRoomType;

public class BuildingVo extends Building {
	private int classroomNum;
	private List<CountRoomType> roomDetail;
	
	public int getClassroomNum() {
		return classroomNum;
	}
	public void setClassroomNum(int classroomNum) {
		this.classroomNum = classroomNum;
	}
	public List<CountRoomType> getRoomDetail() {
		return roomDetail;
	}
	public void setRoomDetail(List<CountRoomType> roomDetail) {
		this.roomDetail = roomDetail;
	}
}
