package com.edu.biz.teachingres.entity;

public enum CourseTestWay {
	//课程考试方式
	written("笔试"), practice("实践");
	
	private String name;
    private CourseTestWay(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
