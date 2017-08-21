package com.edu.biz.teachingres.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class BuildingRoom extends BaseEntity {
	@ApiModelProperty(value = " 层数")
	private String floor;
	
	@NotEmpty(message = "名称不能为空")
	@ApiModelProperty(value = "教室名称")
	private String name;
	
	@ApiModelProperty(value = "教室类型")
	@ManyToOne(targetEntity = RoomType.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "room-type",referencedColumnName="dictKey")
	private RoomType roomType;
	
	@ApiModelProperty(value = "座位数")
	private String seatNum;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
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

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = new RoomType();
		this.roomType.setDictKey(roomType);
	}
}
