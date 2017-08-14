package com.edu.biz.schoolroll.entity.pojo;

import com.edu.biz.schoolroll.entity.Classroom;

public class ClassroomVo extends Classroom {

	private Long studentNum;
	private Long femaleNum;
	private Long maleNum;
	public Long getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(Long studentNum) {
		this.studentNum = studentNum;
	}
	public Long getFemaleNum() {
		return femaleNum;
	}
	public void setFemaleNum(Long femaleNum) {
		this.femaleNum = femaleNum;
	}
	public Long getMaleNum() {
		return maleNum;
	}
	public void setMaleNum(Long maleNum) {
		this.maleNum = maleNum;
	}


}
