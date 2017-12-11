package com.edu.biz.teachingres.entity;

public enum RoomType {
	//教室类型
	normal("普通教室"), multimedia("多媒体教室"),ladder("阶梯教室"),practice("实训教室");
	
	private String name;
    private RoomType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
