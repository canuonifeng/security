package com.edu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.teachingresources.entity.Teacher;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/teacher")
@Api("师资")
public class TeacherController extends BaseController<Teacher> {
	
}
