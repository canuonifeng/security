package com.edu.biz.schoolroll.entity;

public enum StudentOrigin {
	//学生来源
	alone("单招"), unified("统考"),selftaught("自考");
	
	private String name;
    private StudentOrigin(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
