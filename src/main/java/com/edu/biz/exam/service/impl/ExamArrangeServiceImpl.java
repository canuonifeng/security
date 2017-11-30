package com.edu.biz.exam.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.exam.dao.ExamArrangeDao;
import com.edu.biz.exam.dao.specification.ExamArrangeSpecification;
import com.edu.biz.exam.entity.ExamAboutFacultyAndGradeAndTestWay;
import com.edu.biz.exam.entity.ExamArrange;
import com.edu.biz.exam.service.ExamArrangeService;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.ProgramService;
import com.edu.biz.teaching.service.TermService;
import com.edu.biz.teaching.specification.ExamCourseSpecification;
import com.edu.biz.teachingres.dao.CourseDao;
import com.edu.biz.teachingres.entity.Course;

@Service
public class ExamArrangeServiceImpl extends BaseService implements ExamArrangeService {
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private ProgramService programService;
	@Autowired
	private TermService TermService;
	@Autowired
	ExamArrangeDao examArrangeDao;
	@Autowired
	CourseDao courseDao;
	
	public void createExamArrange(ExamArrange examArrange) {
		examArrangeDao.save(examArrange);
	}

	public List<ExamArrange> findExamArranges(Map<String, Object> conditions) {
		return examArrangeDao.findAll(new ExamArrangeSpecification(conditions));
	}
	
	@Override
	public List<ExamAboutFacultyAndGradeAndTestWay> getExamList(Map<String, Object> conditions) {
		List<ExamAboutFacultyAndGradeAndTestWay> list = new ArrayList<>();
		List<Faculty> facultys = facultyService.findFacultys(conditions);
		for (Faculty faculty:facultys) {
			Map<String, Object> classroomMap = new HashMap<>();
			classroomMap.put("grade", conditions.get("grade"));
			classroomMap.put("facultyId", faculty.getId());
			Long classroomCount = classroomService.countClassroom(classroomMap);
			Term currenTerm = TermService.getTermByCurrent(1);
			int courseCount = programService.countWrittenProgramCourses(conditions.get("grade").toString(), faculty.getId(), "written", currenTerm.getCode());
			ExamAboutFacultyAndGradeAndTestWay examList = new ExamAboutFacultyAndGradeAndTestWay();
			examList.setFaculty(faculty);
			examList.setGrade(conditions.get("grade").toString());
			examList.setClassNumber(Integer.valueOf(classroomCount.toString()));
			examList.setExamNumber(courseCount);
			list.add(examList);
		}
		
		return list;
	}
	
	@Override
	public List<Course> findExamArrangeCourses(Map<String, Object> conditions) {
		return courseDao.findAll(new ExamCourseSpecification(conditions));
	}
}
