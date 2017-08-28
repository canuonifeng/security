package com.edu.biz.teachingres.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Building extends BaseEntity {
	@NotEmpty(message = "名称不能为空")
	@ApiModelProperty(value = " 名称")
	private String name;
	
	@OneToMany(cascade = CascadeType.REMOVE,targetEntity = BuildingRoom.class,  fetch = FetchType.LAZY)
	@ApiModelProperty(value = "教室")
	@JoinColumn(name = "building_id")
	@JsonIgnore
	private List<BuildingRoom> buildingRoom;

	public List<BuildingRoom> getBuildingRoom() {
		return buildingRoom;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
