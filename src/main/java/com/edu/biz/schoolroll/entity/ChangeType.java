package com.edu.biz.schoolroll.entity;

public enum ChangeType {
	//异动类型
	changeClassroom("班级变更"), changeMajor("专业变更"),fired("开除学籍"),dropOut("退学"), changeSchool("转学"), pause("休学"), retainAdmission("保留入学资格"), back("复学");
	
	private String name;
    private ChangeType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
