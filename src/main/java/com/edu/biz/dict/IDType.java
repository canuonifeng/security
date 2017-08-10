package com.edu.biz.dict;

public enum IDType {

	idcard("身份证"),passport("护照");
	private String name;
    private IDType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public static IDType nameOf(String name) {
		for (IDType type : values()) {
			if (type.name.equals(name)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid ApiAppStatus name: "
				+ name);
	}
}
