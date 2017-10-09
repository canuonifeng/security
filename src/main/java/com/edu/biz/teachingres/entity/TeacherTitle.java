package com.edu.biz.teachingres.entity;

public enum TeacherTitle {
	primary("初级教师"), intermediate("中级教师"),senior("高级教师");
	
	private String name;
    private TeacherTitle(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
