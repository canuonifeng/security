package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teachingres.entity.BuildingRoom;

@Entity
@Table(name="schedule_cycle")
public class ScheduleCycle extends BaseEntity {
	private int week;
	private String period;
	
	@ManyToOne(targetEntity = ClassSchedule.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private ClassSchedule classSchedule;
	
	@ManyToOne(targetEntity = BuildingRoom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "building_room_id")
	private BuildingRoom buildingRoom;
	
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public ClassSchedule getClassSchedule() {
		return classSchedule;
	}

	public void setClassSchedule(ClassSchedule classSchedule) {
		this.classSchedule = classSchedule;
	}

	public BuildingRoom getBuildingRoom() {
		return buildingRoom;
	}

	public void setBuildingRoom(BuildingRoom buildingRoom) {
		this.buildingRoom = buildingRoom;
	}
	
}
