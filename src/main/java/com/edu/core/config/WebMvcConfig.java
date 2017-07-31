package com.edu.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.edu.core.TimeCostInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	TimeCostInterceptor timeCostInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(timeCostInterceptor).addPathPatterns("/**");
	}
}
