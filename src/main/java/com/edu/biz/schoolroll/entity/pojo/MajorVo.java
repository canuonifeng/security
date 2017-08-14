package com.edu.biz.schoolroll.entity.pojo;

import com.edu.biz.schoolroll.entity.Major;

public class MajorVo extends Major {
	private int studentNum;
	private int femaleNum;
	private int maleNum;
	private int unAssginFemalNum;
	private int unAssginMaleNum;

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

	public int getUnAssginFemalNum() {
		return unAssginFemalNum;
	}

	public void setUnAssginFemalNum(int unAssginFemalNum) {
		this.unAssginFemalNum = unAssginFemalNum;
	}

	public int getUnAssginMaleNum() {
		return unAssginMaleNum;
	}

	public void setUnAssginMaleNum(int unAssginMaleNum) {
		this.unAssginMaleNum = unAssginMaleNum;
	}
}
