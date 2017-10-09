package com.edu.biz.teachingres.entity;

public enum TeacherDegree {
	primary("小学"), juniorHigh("初中"),high("高中"),university("大学"),graduateStudent("研究生"),master("硕士"),doctor("博士");
	
	private String name;
    private TeacherDegree(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
