package com.edu.biz.teachingres.entity;

public enum CourseStatus {
	enable("正常"), disable("禁用"),delete("删除");
	
	private String name;
    private CourseStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}