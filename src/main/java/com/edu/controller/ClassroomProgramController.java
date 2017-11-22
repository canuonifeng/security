package com.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.entity.pojo.ProgramCourseVo;
import com.edu.biz.teaching.service.ClassroomProgramService;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.TeachingresJsonViews;
import com.edu.core.util.BeanUtils;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/classroomprogram")
@Api("班级教学计划")
public class ClassroomProgramController extends BaseController<Classroom> {

	@Autowired
	private ClassroomProgramService classroomProgramService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private TermService termService;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private GradedTeachingService gradedTeachingService;

	@RequestMapping(path = "/set/programcourse/{id}/information", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('programCourse', 'edit')")
	public ProgramCourse edit(@PathVariable Long id, @RequestBody Map<String, Object> map) {
		return classroomProgramService.setProgramCourse(id, map);
	}

	@RequestMapping(path = "/{classroomId}/allcourses", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('classroom', 'get')")
	@JsonView({ TeachingresJsonViews.CascadeTeacher.class })
	public List<ProgramCourseVo> courses(@PathVariable Long classroomId, @RequestParam Map<String, Object> conditions) {
		List<ProgramCourse> programCourses = programService.searchAllProgramCourse(conditions);
		List<ProgramCourseVo> programCourseVos = new ArrayList<ProgramCourseVo>();
		for (ProgramCourse programCourse : programCourses) {
			ProgramCourseVo programCourseVo = new ProgramCourseVo();
			BeanUtils.copyPropertiesWithIgnoreProperties(programCourse, programCourseVo);
			programCourseVo = buildProgramCourseVo(classroomId, programCourseVo);
			programCourseVos.add(programCourseVo);
		}
		return programCourseVos;
	}

	private ProgramCourseVo buildProgramCourseVo(Long classroomId, ProgramCourseVo programCourseVo) {
		if (programCourseVo.getMergeClassroomIds() != null) {
			String[] classroomIds = programCourseVo.getMergeClassroomIds().split(",");
			if (classroomIds.length == 0) {
				return programCourseVo;
			}
			List<Long> ids = new ArrayList<>();
			for (int i = 0; i < classroomIds.length; i++) {
				ids.add(Long.parseLong(classroomIds[i]));
			}
			Map<String, Object> conditions = new HashMap<>();
			if (ids.size() > 0) {
				conditions.put("classroomIds", ids);
				List<Classroom> classrooms = classroomService.findClassrooms(conditions);
				programCourseVo.setMergeClassroom(classrooms);
			}
		}
		Term term = termService.getTermByCurrent(1);
		Map<String, Object> map = new HashMap<>();
		map.put("termCode", term.getCode());
		map.put("courseId", programCourseVo.getCourse().getId());
		GradedTeaching gradedTeaching = gradedTeachingService.getGradedTeaching(map);
		if(gradedTeaching != null) {
			for (Classroom classroom : gradedTeaching.getClassrooms()) {
				if (classroomId.equals(classroom.getId())) {
					programCourseVo.setIsGradedCourse(true);
					break;
				}
			}
		}
		return programCourseVo;
	}
}
