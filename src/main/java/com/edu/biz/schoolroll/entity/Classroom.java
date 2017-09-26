package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teaching.entity.Program;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Classroom extends BaseEntity {
	private String code;
	private String name;
	private String grade;
	private int isAssignNum;
	private Integer lastAssignNum;

	public Integer getLastAssignNum() {
		return lastAssignNum;
	}

	public void setLastAssignNum(Integer lastAssignNum) {
		this.lastAssignNum = lastAssignNum;
	}

	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
	@ApiModelProperty(value = "专业")
	private Major major;

	@ManyToOne(targetEntity = Program.class, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "major_id", referencedColumnName = "major_id", insertable = false, updatable = false),
			@JoinColumn(name = "grade", referencedColumnName = "grade", insertable = false, updatable = false) })
	@JsonProperty(access = Access.WRITE_ONLY)
	private Program program;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public int getIsAssignNum() {
		return isAssignNum;
	}

	public void setIsAssignNum(int isAssignNum) {
		this.isAssignNum = isAssignNum;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
}
