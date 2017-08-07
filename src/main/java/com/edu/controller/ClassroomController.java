package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.pojo.FacultyVo;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/classroom")
@Api("班级")
public class ClassroomController extends BaseController<Classroom> {
	@Autowired
	private ClassroomService classroomService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('classroom', 'add')")
	public Classroom add(@RequestBody Classroom classroom) {
		return classroomService.createClassroom(classroom);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('faculty', 'edit')")
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
	@PreAuthorize("hasPermission('faculty', 'get')")
	public Classroom get(@PathVariable Long id) {
		Classroom classroom = new Classroom();
		classroom.setId(id);
		return classroomService.getClassroom(classroom.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('faculty', 'get')")
	public Page<Classroom> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Classroom> page = classroomService.searchClassroom(conditions, pageable);
		
//		List<FacultyVo> facultyVos = new ArrayList<FacultyVo>();
//		for (Faculty faculty: page.getContent()) {
//			FacultyVo facultyVo = new FacultyVo();
//			BeanUtils.copyPropertiesWithIgnoreProperties(faculty, facultyVo);
//			HashMap<String,Object> map=new HashMap<String,Object>();
//			map.put("facultyId", faculty.getId());
//			Long majorNum = majorService.countMajor(map);
//			facultyVo.setMajorNum(majorNum.intValue());
//			facultyVos.add(facultyVo);
//		}

//		Page<FacultyVo> facultyVoPage = new PageImpl<>(facultyVos, pageable, page.getTotalElements());
		return page;
	}
}
