package com.edu.biz.schoolroll.entity;

public enum ChangeStatus {
	facultyApproving("待系部审核"), schoolApproving("待校部审核"),approved("审核通过"),unApproved("审核失败");
	
	private String name;
    private ChangeStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
