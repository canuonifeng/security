package com.edu.biz.schoolroll.entity;

public enum MajorStatus {
	//专业状态
	enable("正常"), disable("禁用");
	
	private String name;
    private MajorStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
