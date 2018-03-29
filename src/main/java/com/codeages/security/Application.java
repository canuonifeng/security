package com.codeages.security;

import org.springframework.boot.SpringApplication;

import com.codeages.framework.FrameworkApplication;
import com.codeages.framework.property.PropertiesListener;

    
public class Application extends FrameworkApplication{
	public static void main(String[] args) throws Exception {
		SpringApplication application = new SpringApplication(Application.class);
		application.addListeners(new PropertiesListener("classpath: config/application.properties"));
		application.run(args);
	}
}