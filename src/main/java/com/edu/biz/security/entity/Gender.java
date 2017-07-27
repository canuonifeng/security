package com.edu.biz.security.entity;

public enum Gender {

	male("男"),female("女"),secret("保密");
	
	private String name;
    private Gender(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
