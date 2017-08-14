package com.edu.biz.schoolroll.entity.pojo;

import com.edu.biz.schoolroll.entity.Major;

public class MajorVo extends Major {
	private Long studentNum;
	private Long femaleNum;
	private Long maleNum;
	private Long unAssginFemalNum;
	private Long unAssginMaleNum;

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

	public Long getUnAssginFemalNum() {
		return unAssginFemalNum;
	}

	public void setUnAssginFemalNum(Long unAssginFemalNum) {
		this.unAssginFemalNum = unAssginFemalNum;
	}

	public Long getUnAssginMaleNum() {
		return unAssginMaleNum;
	}

	public void setUnAssginMaleNum(Long unAssginMaleNum) {
		this.unAssginMaleNum = unAssginMaleNum;
	}

}
