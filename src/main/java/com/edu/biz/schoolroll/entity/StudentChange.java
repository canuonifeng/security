package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.security.entity.User;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class StudentChange extends BaseEntity {
	@ManyToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	@ApiModelProperty(value = "学生")
	private Student student;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "异动类型不能为空")
	@ApiModelProperty(value = "异动类型")
	private ChangeType changeType;
	
	@ApiModelProperty(value = "异动原因")
	private String cause;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "学生异动状态")
	private ChangeStatus status = ChangeStatus.facultyApproving;
	
	@ApiModelProperty(value = "驳回原因")
	private String refuseCause;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "异动前学生状态")
	@NotFound(action = NotFoundAction.IGNORE)
	private StudentStatus oldStudentStatus = StudentStatus.enable;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "异动后学生状态")
	@NotFound(action = NotFoundAction.IGNORE)
	private StudentStatus newStudentStatus = StudentStatus.enable;
	
	@ApiModelProperty(value = "保留年限")
	private String keepYear;
	
	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "old_major_id")
	@ApiModelProperty(value = "异动前学生专业")
	private Major oldMajor;
	
	@ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "new_major_id")
	@ApiModelProperty(value = "异动后学生专业")
	private Major newMajor;
	
	@ManyToOne(targetEntity = Classroom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "old_classroom_id")
	@ApiModelProperty(value = "异动前学生班级")
	private Classroom oldClassroom;
	
	@ManyToOne(targetEntity = Classroom.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "new_classroom_id")
	@ApiModelProperty(value = "异动后学生班级")
	private Classroom newClassroom;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_user_id")
	@ApiModelProperty(value = "最后审核人")
	private User auditUser;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public String getChangeTypeName() {
		return changeType.getName();
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}
	
	public String getStatusName() {
		return status.getName();
	}

	public ChangeStatus getStatus() {
		return status;
	}

	public void setStatus(ChangeStatus status) {
		this.status = status;
	}

	public String getRefuseCause() {
		return refuseCause;
	}

	public void setRefuseCause(String refuseCause) {
		this.refuseCause = refuseCause;
	}
	
	public String getOldStudentStatusName() {
		return oldStudentStatus.getName();
	}

	public StudentStatus getOldStudentStatus() {
		return oldStudentStatus;
	}

	public void setOldStudentStatus(StudentStatus oldStudentStatus) {
		this.oldStudentStatus = oldStudentStatus;
	}
	
	public String getNewStudentStatusName() {
		return newStudentStatus.getName();
	}

	public StudentStatus getNewStudentStatus() {
		return newStudentStatus;
	}

	public void setNewStudentStatus(StudentStatus newStudentStatus) {
		this.newStudentStatus = newStudentStatus;
	}

	public String getKeepYear() {
		return keepYear;
	}

	public void setKeepYear(String keepYear) {
		this.keepYear = keepYear;
	}

	public Major getOldMajor() {
		return oldMajor;
	}

	public void setOldMajor(Major oldMajor) {
		this.oldMajor = oldMajor;
	}

	public Major getNewMajor() {
		return newMajor;
	}

	public void setNewMajor(Major newMajor) {
		this.newMajor = newMajor;
	}

	public Classroom getOldClassroom() {
		return oldClassroom;
	}

	public void setOldClassroom(Classroom oldClassroom) {
		this.oldClassroom = oldClassroom;
	}

	public Classroom getNewClassroom() {
		return newClassroom;
	}

	public void setNewClassroom(Classroom newClassroom) {
		this.newClassroom = newClassroom;
	}

	public User getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(User auditUser) {
		this.auditUser = auditUser;
	}
}
