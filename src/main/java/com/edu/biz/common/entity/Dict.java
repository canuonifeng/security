package com.edu.biz.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "groupCode")
@Table(name = "dict")
public class Dict implements Serializable {

	@Id
	private String dictKey;
	
	private String dictValue;
	
	private int seq;
	
	@CreatedDate
	@JsonProperty(access = Access.READ_ONLY)
	@ApiModelProperty(value = "创建时间", readOnly = true)
	private Date createdTime;

	@LastModifiedDate
	@JsonProperty(access = Access.READ_ONLY)
	@ApiModelProperty(value = "更新时间", readOnly = true)
	private Date updatedTime;

	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}
