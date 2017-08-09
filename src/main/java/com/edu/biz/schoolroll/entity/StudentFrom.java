package com.edu.biz.schoolroll.entity;

public enum StudentFrom {
	alone("单招"), unified("统考"),selftaught("自考");
	
	private String name;
    private StudentFrom(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
