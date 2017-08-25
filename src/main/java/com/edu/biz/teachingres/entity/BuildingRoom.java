package com.edu.biz.teachingres.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class BuildingRoom extends BaseEntity {
	@ApiModelProperty(value = " 层数")
	private Long floor;
	
	@NotEmpty(message = "名称不能为空")
	@ApiModelProperty(value = "教室名称")
	private String name;
	
	@ManyToOne(targetEntity = Building.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "building_id")
	@ApiModelProperty(value = "建筑")
	private Building building;
	
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "教室类型")
	private RoomType roomType;
	
	@ApiModelProperty(value = "座位数")
	private String seatNum;

	public Long getFloor() {
		return floor;
	}

	public void setFloor(Long floor) {
		this.floor = floor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	
	public String getRoomTypeName() {
		return roomType.getName();
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
}
