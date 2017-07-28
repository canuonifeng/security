package com.edu.biz.security.entity;

public enum UserStatus {
	enable("正常"), disable("禁用"),delete("删除");
	
	private String name;
    private UserStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
