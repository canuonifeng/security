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
import com.edu.biz.teachingres.entity.CourseCategory;
import com.edu.biz.teachingres.entity.CourseNature;
import com.edu.biz.teachingres.entity.CourseTestWay;
import com.edu.biz.teachingres.entity.RoomType;
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

	@RequestMapping(path = "/courseCategory", method = RequestMethod.GET)
	public List<Object> getCourseCategory() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(CourseCategory courseCategory : CourseCategory.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", courseCategory.name());
			map.put("name" , courseCategory.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/courseNature", method = RequestMethod.GET)
	public List<Object> getCourseNature() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(CourseNature courseNature : CourseNature.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", courseNature.name());
			map.put("name" , courseNature.getName());
			list.add(map);
		}
		return list;
	}

	@RequestMapping(path = "/roomtype", method = RequestMethod.GET)
	public List<Object> getCourseTestWay() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(RoomType roomType : RoomType.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", roomType.name());
			map.put("name" , roomType.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/courseTestWay", method = RequestMethod.GET)
	public List<Object> getRoomType() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(CourseTestWay courseTestWay : CourseTestWay.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", courseTestWay.name());
			map.put("name" , courseTestWay.getName());
			list.add(map);
		}
		return list;
	}
}
