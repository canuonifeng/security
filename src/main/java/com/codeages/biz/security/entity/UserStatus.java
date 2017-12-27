package com.codeages.biz.security.entity;

public enum UserStatus {
	//用户状态
	enable("正常"), disable("禁用"),delete("删除");
	
	private String name;
    private UserStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
