package com.edu.biz.schoolroll.entity;

public enum StudentStatus {
	enable("正常"), disable("禁用"),delete("删除"),changing("异动中");
	
	private String name;
    private StudentStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
