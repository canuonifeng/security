package com.edu.biz.dict;

public enum Gender {
	//性别
	male("男"),female("女"),secret("保密");
	
	private String name;
    private Gender(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public static Gender nameOf(String name) {
		for (Gender type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid ApiAppStatus name: "
				+ name);
	}
}
