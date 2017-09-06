package com.edu.biz.schoolroll.entity;

public enum StudentStatus {
	enable("正常"), dropOut("退学"), pause("休学"), fired("开除"), retainAdmission("保留学籍");
	
	private String name;
    private StudentStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
