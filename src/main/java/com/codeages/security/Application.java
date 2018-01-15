package com.codeages.security;

import org.springframework.boot.SpringApplication;

import com.codeages.framework.FrameworkApplication;

    
public class Application extends FrameworkApplication{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}