package com.edu.biz.schoolroll.entity;

public enum MajorStatus {
	enable("正常"), disable("禁用");
	
	private String name;
    private MajorStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
