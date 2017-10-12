package com.edu.biz.schoolroll.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.dict.Gender;
import com.edu.biz.dict.IDType;
import com.edu.biz.dict.Nation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Student extends BaseEntity {
	@ApiModelProperty(value = " 学号")
	private String no;
	
	@NotEmpty(message = "姓名不能为空")
	@ApiModelProperty(value = " 姓名")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 性别")
	private Gender gender = Gender.secret;
	
	@ApiModelProperty(value="入学年月")
	private String admissionTime;
	
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
	
	@OneToMany(targetEntity = StudentChange.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	@ApiModelProperty(value = "异动记录")
	private List<StudentChange> changes;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 状态")
	private StudentStatus status;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 学生来源")
	private StudentOrigin origin = StudentOrigin.unified;
	
	@ApiModelProperty(value = "籍贯")
	private String nativePlace;
	
	@ApiModelProperty(value = "身份证号")
	private String idcard;

	@ApiModelProperty(value = "出生日期")
	private String birthday;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "民族")
	private Nation nation = Nation.HAN;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "证件类型")
	private IDType idtype = IDType.idcard;
	
	@ApiModelProperty(value = "排序")
	private int seq;
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getNationName() {
		return nation.getName();
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}
	
	public String getIdtypeName() {
		return idtype.getName();
	}

	public IDType getIdtype() {
		return idtype;
	}

	public void setIdtype(IDType idtype) {
		this.idtype = idtype;
	}

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
	
	public String getOriginName() {
		return origin.getName();
	}

	public StudentOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(StudentOrigin origin) {
		this.origin = origin;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
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

	public String getAdmissionTime() {
		return admissionTime;
	}

	public void setAdmissionTime(String admissionTime) {
		this.admissionTime = admissionTime;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public List<StudentChange> getChanges() {
		return changes;
	}

	public void setChanges(List<StudentChange> changes) {
		this.changes = changes;
	}
}
