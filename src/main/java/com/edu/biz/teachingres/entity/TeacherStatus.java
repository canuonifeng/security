package com.edu.biz.teachingres.entity;

public enum TeacherStatus {
	//教师状态
	enable("在职"), vacation("休假"),retired("退休"),departure("离职"),learnOut("外出学习");
	
	private String name;
    private TeacherStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
