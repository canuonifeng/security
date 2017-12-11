package com.edu.biz.teachingres.entity;

public enum CourseNature {
	//课程性质
	compulsory("必修课"), elective("选修课"),optional("任选课"),limit("限选课");
	
	private String name;
    private CourseNature(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
