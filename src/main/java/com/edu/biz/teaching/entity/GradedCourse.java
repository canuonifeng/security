package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teachingres.entity.Teacher;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="graded_course")
public class GradedCourse extends BaseEntity {
	@ManyToOne(targetEntity = GradedTeaching.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "graded_id")
	@ApiModelProperty(value = "分层教学")
	private GradedTeaching gradedTeaching;
	
	@ManyToOne(targetEntity = GradedRank.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "rank_id")
	@ApiModelProperty(value = "分层等级")
	private GradedRank gradedRank;
	
	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	@ApiModelProperty(value = "教师")
	private Teacher teacher;
}
