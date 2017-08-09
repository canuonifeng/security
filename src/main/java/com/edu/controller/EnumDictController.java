package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.StudentFrom;
import com.edu.biz.schoolroll.entity.StudentStatus;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/enum/dict")
@Api("枚举数据字典")
public class EnumDictController {
	@RequestMapping(path = "/studentFrom", method = RequestMethod.GET)
	public List<Object> getStudentFrom() {
		ArrayList<Object> list=new ArrayList<Object>();
		for(StudentFrom studentFrom : StudentFrom.values()) {
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
}
