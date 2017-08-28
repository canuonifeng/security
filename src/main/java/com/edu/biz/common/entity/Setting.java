package com.edu.biz.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

@Entity
@Table(name = "sys_setting")
public class Setting extends BaseEntity {

	private String code;
	private String value;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
