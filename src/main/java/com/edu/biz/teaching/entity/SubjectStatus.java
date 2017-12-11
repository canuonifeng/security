package com.edu.biz.teaching.entity;

public enum SubjectStatus {
	//科目状态
	enable("正常"), disable("禁用");
	
	private String name;
    private SubjectStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
