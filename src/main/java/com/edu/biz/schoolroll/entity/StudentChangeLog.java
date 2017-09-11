package com.edu.biz.schoolroll.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.security.entity.User;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class StudentChangeLog extends BaseEntity {
	@ManyToOne(targetEntity = StudentChange.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "change_id")
	@ApiModelProperty(value = "学籍异动")
	private StudentChange change;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "操作前状态")
	private ChangeStatus oldStatus;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "操作后状态")
	private ChangeStatus newStatus;
	
	@ApiModelProperty(value = "驳回原因")
	private String cause;
	
	@ApiModelProperty(value = "备注")
	private String remark;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "opUser_id")
	@ApiModelProperty(value = "操作人")
	private User opUser;

	public StudentChange getChange() {
		return change;
	}

	public void setChange(StudentChange change) {
		this.change = change;
	}

	public ChangeStatus getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(ChangeStatus oldStatus) {
		this.oldStatus = oldStatus;
	}

	public ChangeStatus getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(ChangeStatus newStatus) {
		this.newStatus = newStatus;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getOpUser() {
		return opUser;
	}

	public void setOpUser(User opUser) {
		this.opUser = opUser;
	}
}
