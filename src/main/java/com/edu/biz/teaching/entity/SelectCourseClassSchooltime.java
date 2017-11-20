package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teachingres.entity.BuildingRoom;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="select_course_class_schooltime")
public class SelectCourseClassSchooltime extends BaseEntity {
	@OneToOne(targetEntity = SelectCourseSchooltime.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "schooltime_id")
	@ApiModelProperty(value = "上课时间")
	private SelectCourseSchooltime selectCourseSchooltime;
	
	@ManyToOne(targetEntity = SelectCourseClass.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "select_course_class_id")
	@ApiModelProperty(value = "课堂")
	private SelectCourseClass selectCourseClass;
	
	@ManyToOne(targetEntity = BuildingRoom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "building_room_id")
	@ApiModelProperty(value = "教室")
	private BuildingRoom buildingRoom;

	public SelectCourseSchooltime getSelectCourseSchooltime() {
		return selectCourseSchooltime;
	}

	public void setSelectCourseSchooltime(SelectCourseSchooltime selectCourseSchooltime) {
		this.selectCourseSchooltime = selectCourseSchooltime;
	}

	public SelectCourseClass getSelectCourseClass() {
		return selectCourseClass;
	}

	public void setSelectCourseClass(SelectCourseClass selectCourseClass) {
		this.selectCourseClass = selectCourseClass;
	}

	public BuildingRoom getBuildingRoom() {
		return buildingRoom;
	}

	public void setBuildingRoom(BuildingRoom buildingRoom) {
		this.buildingRoom = buildingRoom;
	}
}	
