package com.edu.biz.teaching.entity;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class term extends BaseEntity {
	@NotEmpty(message = "标题不能为空")
	@ApiModelProperty(value = "标题")
	private String title;
	
	@ApiModelProperty(value = "短代码")
	private String code;
	
	@ApiModelProperty(value = "长代码")
	private String longCode;
}
