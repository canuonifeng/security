package com.edu.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.pojo.ClassroomForm;
import com.edu.biz.schoolroll.entity.pojo.ClassroomVo;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.MajorService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/classroom")
@Api("班级")
public class ClassroomController extends BaseController<Classroom> {
	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private MajorService majorService;

	@Autowired
	private StudentService studentService;

	@RequestMapping(path = "/batch", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classroom', 'add')")
	public Boolean batchAdd(@RequestBody ClassroomForm form) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("majorId", form.getMajorId());
		Long classroomNum = classroomService.countClassroom(map);
		Major major = majorService.getMajor(form.getMajorId());
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String yearSuffix = String.valueOf(year).substring(String.valueOf(year).length() - 2);
		DecimalFormat dfInt = new DecimalFormat("00");

		for (int i = 0; i < form.getNum(); i++) {
			String num = dfInt.format(classroomNum.intValue() + 1);

			Classroom classroom = new Classroom();
			classroom.setGrade(form.getGrade());
			String name = form.getClassroomPrefix() + num + form.getClassroomSuffix();
			classroom.setName(name);
			classroom.setMajor(major);

			String code = yearSuffix + major.getCode() + num;
			classroom.setCode(code);
			classroomService.createClassroom(classroom);

			classroomNum++;
		}
		return true;
	}


	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('classroom', 'edit')")
	public Classroom edit(@PathVariable Long id, @RequestBody Classroom classroom) {
		classroom.setId(id);
		return classroomService.updateClassroom(classroom);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('classroom', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return classroomService.deleteClassroom(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Classroom get(@PathVariable Long id) {
		Classroom classroom = classroomService.getClassroom(id);
		ClassroomVo classroomVo = new ClassroomVo();
		BeanUtils.copyPropertiesWithIgnoreProperties(classroom, classroomVo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("classroomId", classroom.getId());
		Long studentNum = studentService.countStudent(map);
		classroomVo.setStudentNum(studentNum);

		map.clear();
		map.put("classroomId", classroom.getId());
		map.put("gender", "male");
		Long maleNum = studentService.countStudent(map);
		classroomVo.setMaleNum(maleNum);

		map.clear();
		map.put("classroomId", classroom.getId());
		map.put("gender", "female");
		Long femaleNum = studentService.countStudent(map);
		classroomVo.setFemaleNum(femaleNum);
		return classroomVo;
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Page<ClassroomVo> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Classroom> page = classroomService.searchClassroom(conditions, pageable);
		List<ClassroomVo> classroomVos = new ArrayList<ClassroomVo>();
		for (Classroom classroom : page.getContent()) {
			ClassroomVo classroomVo = new ClassroomVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(classroom, classroomVo);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("classroomId", classroom.getId());
			Long memberNum = studentService.countStudent(map);
			classroomVo.setStudentNum(memberNum);
			classroomVos.add(classroomVo);
		}

		Page<ClassroomVo> classroomVoPage = new PageImpl<>(classroomVos, pageable, page.getTotalElements());
		return classroomVoPage;
	}

	@RequestMapping(path = "/all", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public List<Classroom> findAllClassroom(@RequestParam Map<String, Object> conditions) {
		List<Classroom> list = classroomService.findAllClassroom(conditions);
		return list;
	}
}
