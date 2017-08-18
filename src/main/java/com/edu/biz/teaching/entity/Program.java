package com.edu.biz.teaching.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.dict.Gender;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Program extends BaseEntity {

}
