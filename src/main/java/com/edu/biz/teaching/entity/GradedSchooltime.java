package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="graded_schooltime")
public class GradedSchooltime extends BaseEntity {
	@ManyToOne(targetEntity = GradedTeaching.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "graded_id")
	@ApiModelProperty(value = "分层教学")
	private GradedTeaching gradedTeaching;
	
	@ApiModelProperty(value = "星期几")
	private int week;
	
	@ApiModelProperty(value = "时辰")
	private int timeSlot;
	
	@ApiModelProperty(value = "节")
	private int period;
}
