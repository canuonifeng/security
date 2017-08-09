package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.security.entity.Gender;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Student extends BaseEntity {
	@ApiModelProperty(value = " 学号")
	private String no;
	
	@NotEmpty(message = "姓名不能为空")
	@ApiModelProperty(value = " 姓名")
	private String name;
	
	@NotEmpty(message = "姓名不能为空")
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 性别")
	private Gender gender;
	
	@ApiModelProperty(value="入学年月")
	private String year_month;
	
	@ApiModelProperty(value="年级")
	private String grade;
	
	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
	@ApiModelProperty(value = "所属专业")
	private Major major;
	
	@ManyToOne(targetEntity = Classroom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "classroom_id")
	@ApiModelProperty(value = "所属班级")
	private Classroom classroom;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private StudentStatus status;
	
	@NotEmpty(message = "学生来源不能为空")
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 学生来源")
	private StudentFrom from;
	
	@NotEmpty(message = "籍贯不能为空")
	@ApiModelProperty(value = "籍贯")
	private String native_place;
	
	@NotEmpty(message = "身份证号不能为空")
	@ApiModelProperty(value = "身份证号")
	private String idcard;
	
	@ApiModelProperty(value = "备注")
	private String remark;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public String getGenderName() {
		return gender.getName();
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
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

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public StudentStatus getStatus() {
		return status;
	}
	
	public String getStatusName() {
		return status.getName();
	}

	public void setStatus(StudentStatus status) {
		this.status = status;
	}
	
	public String getFromName() {
		return from.getName();
	}

	public StudentFrom getFrom() {
		return from;
	}

	public void setFrom(StudentFrom from) {
		this.from = from;
	}

	public String getNative_place() {
		return native_place;
	}

	public void setNative_place(String native_place) {
		this.native_place = native_place;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
