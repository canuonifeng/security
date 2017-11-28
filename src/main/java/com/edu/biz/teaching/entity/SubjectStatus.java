package com.edu.biz.teaching.entity;

public enum SubjectStatus {
	enable("正常"), disable("禁用");
	
	private String name;
    private SubjectStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
