package com.edu.biz.schoolroll.entity.pojo;

import com.edu.biz.schoolroll.entity.Classroom;

public class ClassroomVo extends Classroom {

	private int studentNum;
	private int femaleNum;
	private int maleNum;

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public int getFemaleNum() {
		return femaleNum;
	}

	public void setFemaleNum(int femaleNum) {
		this.femaleNum = femaleNum;
	}

	public int getMaleNum() {
		return maleNum;
	}

	public void setMaleNum(int maleNum) {
		this.maleNum = maleNum;
	}

}
