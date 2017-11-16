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
@Table(name="graded_course_schooltime")
public class GradedCourseSchooltime extends BaseEntity {
	@OneToOne(targetEntity = GradedSchooltime.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "schooltime_id")
	@ApiModelProperty(value = "上课时间")
	private GradedSchooltime gradedSchooltime;
	
	@ManyToOne(targetEntity = GradedCourse.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "graded_course_id")
	@ApiModelProperty(value = "课堂")
	private GradedCourse gradedCourse;
	
	@ManyToOne(targetEntity = BuildingRoom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "building_room_id")
	@ApiModelProperty(value = "教师")
	private BuildingRoom buildingRoom;
}