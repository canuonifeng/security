package com.edu.biz.org.entity;

public enum FacultyStatus {
	enable("正常"), disable("禁用");
	
	private String name;
    private FacultyStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
