package com.edu.controller;

import java.util.ArrayList;
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

import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.pojo.ProgramVo;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/plan")
@Api("班级")
public class ProgramController extends BaseController<Program> {
	@Autowired
	private ProgramService programService;
	@Autowired
	private ClassroomService classroomService;

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('program', 'edit')")
	public Program edit(@PathVariable Long id, @RequestBody Program program) {
		program.setId(id);
		return programService.updateProgram(program);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('classroom', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return programService.deleteProgram(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Program get(@PathVariable Long id) {
		Program program = programService.getProgram(id);
		return program;
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Page<ProgramVo> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Program> page = programService.searchPrograms(conditions, pageable);
		List<ProgramVo> programVos = new ArrayList<ProgramVo>();
		for (Program program : page.getContent()) {
			ProgramVo programVo = new ProgramVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(program, programVo);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("majorId", program.getMajor().getId());
			Long classroomNum = classroomService.countClassroom(map);
			programVo.setClassroomNum(classroomNum);
			programVos.add(programVo);
		}

		Page<ProgramVo> programVoPage = new PageImpl<>(programVos, pageable, page.getTotalElements());
		return programVoPage;
	}
}
