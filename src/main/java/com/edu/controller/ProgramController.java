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

import com.edu.biz.common.util.TermCodeUtil;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.Program;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.entity.pojo.ProgramCourseVo;
import com.edu.biz.teaching.entity.pojo.ProgramVo;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.service.CourseService;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.util.BeanUtils;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/program")
@Api("教学计划")
public class ProgramController extends BaseController<Program> {
	@Autowired
	private ProgramService programService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private TermService termService;
	@Autowired
	private CourseArrangeService courseArrangeService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('major', 'add')")
	public Program add(@RequestBody Program program) {
		return programService.createProgram(program);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('program', 'edit')")
	public Program edit(@PathVariable Long id, @RequestBody Program program) {
		program.setId(id);
		return programService.updateProgram(program);
	}

	@RequestMapping(path = "/{id}/course", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('program', 'edit')")
	public ProgramCourse editProgramCourse(@PathVariable Long id, @RequestBody ProgramCourse programCourse) {
		programCourse.setId(id);
		return programService.updateProgramCourse(programCourse);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('program', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return programService.deleteProgram(id);
	}

	@RequestMapping(path = "/{id}/term", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('program', 'get')")
	public ProgramVo getTermProgram(@PathVariable Long id, @RequestParam Map<String, String> conditions) {
		Program program = programService.getProgram(id);
		ProgramVo programVo = new ProgramVo();
		BeanUtils.copyPropertiesWithIgnoreProperties(program, programVo);

		String code = TermCodeUtil.getTermCode(program.getGrade(), Integer.parseInt(conditions.get("termNum")));
		Term term = termService.getTermByCode(code);
		programVo.setTerm(term);

		return programVo;
	}

	@RequestMapping(path = "/{programCourseId}/course", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('program', 'delete')")
	public boolean deleteCourse(@PathVariable Long programCourseId) {
		return programService.deleteProgramCourse(programCourseId);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Program get(@PathVariable Long id) {
		Program program = programService.getProgram(id);
		return program;
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('program', 'get')")
	public Page<ProgramVo> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Program> page = programService.searchPrograms(conditions, pageable);
		List<ProgramVo> programVos = new ArrayList<ProgramVo>();
		for (Program program : page.getContent()) {
			ProgramVo programVo = new ProgramVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(program, programVo);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("majorId", program.getMajor().getId());
			map.put("grade", program.getGrade());
			Long classroomNum = classroomService.countClassroom(map);
			programVo.setClassroomNum(classroomNum);
			programVos.add(programVo);
		}

		Page<ProgramVo> programVoPage = new PageImpl<>(programVos, pageable, page.getTotalElements());
		return programVoPage;
	}

	@RequestMapping(path = "show/{id}/coursetable", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('program', 'get')")
	public Map<String, Map<String, List<ProgramCourse>>> showCourseTable(@PathVariable Long id) {
		Map<String, Map<String, List<ProgramCourse>>> result = programService.showCourseTable(id);
		return result;
	}

	@RequestMapping(path = "{id}/terms", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('program', 'get')")
	public List<Term> getProgramTerm(@PathVariable Long id) {
		List<Term> result = programService.getProgramTerm(id);
		return result;
	}

	@RequestMapping(path = "/pagecourses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public Page<ProgramCourse> coursePager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<ProgramCourse> programCourse = programService.searchProgramCourse(conditions, pageable);
		return programCourse;
	}

	@RequestMapping(path = "/allcourses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	public List<ProgramCourseVo> termCourses(@RequestParam Map<String, Object> conditions) {
		List<ProgramCourse> programCourses = programService.searchAllProgramCourse(conditions);
		List<ProgramCourseVo> programCourseVos = new ArrayList<>();
		for (ProgramCourse programCourse : programCourses) {
			ProgramCourseVo programCourseVo = buildProgramCourseVo(programCourse, conditions);
			if(programCourseVo.getRemainderCourseNum().equals(0)) {
				continue;
			}
			programCourseVos.add(programCourseVo);
		}
		return programCourseVos;
	}

	@RequestMapping(path = "/{programId}/addcourse", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('course', 'get')")
	public Page<Course> showCoursesNotInProgram(@PathVariable Long programId,
			@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return programService.searchCoursesNotInProgram(programId, conditions, pageable);
	}

	@RequestMapping(path = "/join/{programId}/program", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('program', 'post')")
	public Boolean joinProgram(@PathVariable Long programId, @RequestBody Map<String, String> courseIds) {
		Program program = programService.getProgram(programId);
		for (String key : courseIds.keySet()) {
			Long id = Long.parseLong(courseIds.get(key));
			Course course = courseService.getCourse(id);
			programService.joinProgram(course, program);
		}

		return true;
	}
	
	private ProgramCourseVo buildProgramCourseVo(ProgramCourse programCourse, Map<String, Object> conditions) {
		ProgramCourseVo programCourseVo = new ProgramCourseVo();
		BeanUtils.copyPropertiesWithIgnoreProperties(programCourse, programCourseVo);
		programCourseVo.setRemainderCourseNum(programCourse.getWeekPeriod());
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule(programCourse.getTermCode(), programCourse.getCourse().getId(), Long.parseLong(conditions.get("classroomId").toString()));
		if(classSchedule != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("scheduleId", classSchedule.getId());
			Long arrangeCourseNum = courseArrangeService.countScheduleCyle(map);
			Integer remainderCourseNum = programCourse.getWeekPeriod() - Integer.parseInt(arrangeCourseNum.toString());
			if(remainderCourseNum < 0) {
				throw new InvalidParameterException("课程"+programCourse.getCourse().getName()+"排课超出周课时");
			}
			programCourseVo.setRemainderCourseNum(remainderCourseNum);
		}
		return programCourseVo;
	}
}
