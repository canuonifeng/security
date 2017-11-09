package com.edu.biz.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.dao.ProgramCourseDao;
import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ProgramCourse;
import com.edu.biz.teaching.entity.ScheduleTeacher;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.ClassroomProgramService;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.service.TeacherService;
import com.edu.core.exception.NotFoundException;

@Service
public class ClassroomProgramServiceImpl extends BaseService implements ClassroomProgramService {
	@Autowired
	private ProgramCourseDao programCourseDao;
	@Autowired
	private TermService termService;
	@Autowired
	private CourseArrangeService courseArrangeService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private TeacherService teacherService;

	@Override
	public ProgramCourse setProgramCourse(Long programCourseId, Map<String, Object> map) {
		ProgramCourse saveProgramCourse = programCourseDao.findOne(programCourseId);
		if (null == saveProgramCourse) {
			throw new NotFoundException("该教学计划课程不存在");
		}
		setCourseSchedule(saveProgramCourse.getCourse().getId(), map);
		ProgramCourse programCourse = dealProgramCourse(saveProgramCourse, map);
		return programCourseDao.save(programCourse);
	}

	private Boolean setCourseSchedule(Long courseId, Map<String, Object> map) {
		Term term = termService.getCurrentTerm();
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule = courseArrangeService.getClassSchedule(term.getCode(), courseId);
		if (classSchedule == null) {
			ClassSchedule schedule = new ClassSchedule();
			schedule.setTerm(term.getCode());
			Course course = new Course();
			course.setId(courseId);
			schedule.setCourse(course);
			classSchedule = courseArrangeService.createClassSchedule(schedule);
		}
		if (map.containsKey("teacherId") && map.get("teacherId") != null && !map.get("teacherId").equals("")) {
			setScheduleTeacher(classSchedule, Long.parseLong(map.get("teacherId").toString()));
		}

		if (map.containsKey("mergeClassroomIds") && map.get("mergeClassroomIds") != null
				&& !map.get("mergeClassroomIds").equals("")) {
			setScheduleClassroom(classSchedule, map.get("mergeClassroomIds").toString());
		}
		return true;
	}

	private void setScheduleClassroom(ClassSchedule classSchedule, String mergeClassroomIds) {
		String classroomIds[] = mergeClassroomIds.split(",");
		List<Long> ids = new ArrayList<>();
		for (int i = 0; i < classroomIds.length; i++) {
			ids.add(Long.parseLong(classroomIds[i]));
		}
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("classroomIds", ids);
		List<Classroom> classrooms = classroomService.findClassrooms(conditions);
		classSchedule.setClassrooms(classrooms);
		courseArrangeService.updateClassSchedule(classSchedule);
	}

	private void setScheduleTeacher(ClassSchedule classSchedule, Long teacherId) {
		ScheduleTeacher scheduleTeacher = new ScheduleTeacher();
		Teacher teacher = teacherService.getTeacher(teacherId);
		if (teacher == null) {
			throw new NotFoundException("该老师不存在");
		}
		
		scheduleTeacher.setClassSchedule(classSchedule);
		scheduleTeacher.setTeacher(teacher);
		courseArrangeService.createScheduleTeacher(scheduleTeacher);
	}

	private ProgramCourse dealProgramCourse(ProgramCourse programCourse, Map<String, Object> map) {
		if (map.containsKey("teacherId") && map.get("teacherId") != null) {
			programCourse.setMasterTeacherId(Long.parseLong(map.get("teacherId").toString()));
		}
		if (map.containsKey("mergeClassroomIds") && map.get("mergeClassroomIds") != null
				&& !map.get("mergeClassroomIds").equals("")) {
			programCourse.setMergeClassroomIds(map.get("mergeClassroomIds").toString());
		}
		if (map.containsKey("classWeekStage") && map.get("classWeekStage") != null
				&& !map.get("classWeekStage").equals("")) {
			programCourse.setClassWeekStage(map.get("classWeekStage").toString());
		}
		return programCourse;
	}
}
