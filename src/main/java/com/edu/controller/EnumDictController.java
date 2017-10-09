package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.dict.Gender;
import com.edu.biz.dict.IDType;
import com.edu.biz.dict.Nation;
import com.edu.biz.schoolroll.entity.ChangeType;
import com.edu.biz.schoolroll.entity.StudentOrigin;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.teachingres.entity.CourseCategory;
import com.edu.biz.teachingres.entity.CourseNature;
import com.edu.biz.teachingres.entity.CourseTestWay;
import com.edu.biz.teachingres.entity.RoomType;
import com.edu.biz.teachingres.entity.TeacherDegree;
import com.edu.biz.teachingres.entity.TeacherStatus;
import com.edu.biz.teachingres.entity.TeacherTitle;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/enum/dict")
@Api("枚举数据字典")
public class EnumDictController {
	@RequestMapping(path = "/studentfrom", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/studentstatus", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/teacherstatus", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/nation", method = RequestMethod.GET)
	public List<Object> getNation() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(Nation nation : Nation.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", nation.name());
			map.put("name" , nation.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/idtype", method = RequestMethod.GET)
	public List<Object> getIDType() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(IDType idtype : IDType.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", idtype.name());
			map.put("name" , idtype.getName());
			list.add(map);
		}
		return list;
	}

	@RequestMapping(path = "/coursecategory", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/coursenature", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/coursetestway", method = RequestMethod.GET)
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
	
	@RequestMapping(path = "/changetype", method = RequestMethod.GET)
	public List<Object> getChangeType() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(ChangeType changeType : ChangeType.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", changeType.name());
			map.put("name" , changeType.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/teachertitle", method = RequestMethod.GET)
	public List<Object> getTeacherTitle() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(TeacherTitle teacherTitle : TeacherTitle.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", teacherTitle.name());
			map.put("name" , teacherTitle.getName());
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(path = "/teacherdegree", method = RequestMethod.GET)
	public List<Object> getTeacherDegree() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(TeacherDegree teacherDegree : TeacherDegree.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", teacherDegree.name());
			map.put("name" , teacherDegree.getName());
			list.add(map);
		}
		return list;
	}
}
