package com.edu.biz.schoolroll.entity;

public enum StudentStatus {
	//学生状态
	enable("正常"), dropOut("退学"), pause("休学"),changeSchool("转学"), fired("开除"), retainAdmission("保留学籍"), approving("异动中");
	
	private String name;
    private StudentStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
