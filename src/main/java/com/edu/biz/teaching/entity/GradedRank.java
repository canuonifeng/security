package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="graded_rank")
public class GradedRank extends BaseEntity {
	@ManyToOne(targetEntity = GradedTeaching.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "graded_id")
	@ApiModelProperty(value = "分层教学")
	private GradedTeaching gradedTeaching;
	
	@ApiModelProperty(value = "等级名称")
	private String name;
	
	@ApiModelProperty(value = "最低分数")
	private String minScore;
	
	@ApiModelProperty(value = "最高分数")
	private String maxScore;
}
