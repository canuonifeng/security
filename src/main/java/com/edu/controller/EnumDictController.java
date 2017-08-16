package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.dict.Gender;
import com.edu.biz.schoolroll.entity.StudentOrigin;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.teachingres.entity.TeacherStatus;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/enum/dict")
@Api("枚举数据字典")
public class EnumDictController {
	@RequestMapping(path = "/studentFrom", method = RequestMethod.GET)
	public List<Object> getStudentFrom() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(StudentOrigin studentFrom : StudentOrigin.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", studentFrom.name());
			map.put("name" , studentFrom.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/studentStatus", method = RequestMethod.GET)
	public List<Object> getStudentStatus() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(StudentStatus studentStatus : StudentStatus.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", studentStatus.name());
			map.put("name" , studentStatus.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/teacherStatus", method = RequestMethod.GET)
	public List<Object> getTeacherStatus() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(TeacherStatus teacherStatus : TeacherStatus.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", teacherStatus.name());
			map.put("name" , teacherStatus.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/gender", method = RequestMethod.GET)
	public List<Object> getGender() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(Gender gender : Gender.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", gender.name());
			map.put("name" , gender.getName());
			list.add(map);
		}
		return list;
	}
}
