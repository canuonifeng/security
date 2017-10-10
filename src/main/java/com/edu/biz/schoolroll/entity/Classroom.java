package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.Teacher;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Classroom extends BaseEntity {
	private String code;
	private String name;
	private String grade;
	private int isAssignNum;
	private Integer lastAssignNum = 0;

	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
	@ApiModelProperty(value = "专业")
	private Major major;
	
	@ManyToOne(targetEntity = Program.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "program_id")
	@ApiModelProperty(value = "教学计划")
	private Program program;
	
	@OneToOne(targetEntity = BuildingRoom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	@ApiModelProperty(value = "所属教室")
	private BuildingRoom buildingRoom;
	
	@ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	@ApiModelProperty(value = "班主任")
	private Teacher teacher;

	public Integer getLastAssignNum() {
		return lastAssignNum;
	}

	public void setLastAssignNum(Integer lastAssignNum) {
		this.lastAssignNum = lastAssignNum;
	}

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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public BuildingRoom getBuildingRoom() {
		return buildingRoom;
	}

	public void setBuildingRoom(BuildingRoom buildingRoom) {
		this.buildingRoom = buildingRoom;
	}
}
