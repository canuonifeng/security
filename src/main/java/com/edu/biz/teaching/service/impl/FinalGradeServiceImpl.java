package com.edu.biz.teaching.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.teaching.dao.FinalGradePartCourseDao;
import com.edu.biz.teaching.dao.FinalGradePartDao;
import com.edu.biz.teaching.dao.FinalGradePartStudentDao;
import com.edu.biz.teaching.entity.FinalGradePart;
import com.edu.biz.teaching.entity.FinalGradePartCourse;
import com.edu.biz.teaching.entity.FinalGradePartStudent;
import com.edu.biz.teaching.entity.pojo.FinalGradeCourseForm;
import com.edu.biz.teaching.service.FinalGradeService;
import com.edu.biz.teaching.specification.FinalGradePartCourseSpecification;
import com.edu.biz.teaching.specification.FinalGradePartSpecification;
import com.edu.biz.teaching.specification.FinalGradePartStudentSpecification;
import com.edu.biz.teaching.specification.FinalGradeSpecification;
import com.edu.biz.teachingres.dao.CourseDao;
import com.edu.biz.teachingres.entity.Course;
import com.edu.core.exception.NotFoundException;
import com.edu.core.util.BeanUtils;

@Service
public class FinalGradeServiceImpl extends BaseService implements FinalGradeService {
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private FinalGradePartCourseDao finalGradePartCourseDao;
	@Autowired
	private FinalGradePartStudentDao finalGradePartStudentDao;
	@Autowired
	private FinalGradePartDao finalGradePartDao;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private StudentService studentService;

	@Override
	public List<Course> findFinalGradeCourses(Map<String, Object> conditions) {
		return courseDao.findAll(new FinalGradeSpecification(conditions));
	}

	@Override
	public Boolean setFinalGradePartCourses(FinalGradeCourseForm finalGradeCourseForm) {
		for (String key : finalGradeCourseForm.getGradeCourseScore().keySet()) {
			Map<String, Object> map = new HashMap<>();
			map.put("finalGradePartId", key);
			map.put("facultyId", finalGradeCourseForm.getFacultyId());
			map.put("courseId", finalGradeCourseForm.getCourseId());
			map.put("termCode", finalGradeCourseForm.getTermCode());
			FinalGradePartCourse finalGradePartCourse = finalGradePartCourseDao
					.findOne(new FinalGradePartCourseSpecification(map));

			FinalGradePartCourse createFinalGradePartCourse = new FinalGradePartCourse();
			Course course = courseDao.findOne(finalGradeCourseForm.getCourseId());
			Faculty faculty = facultyService.getFaculty(finalGradeCourseForm.getFacultyId());
			FinalGradePart finalGradePart = getFinalGradePart(Long.parseLong(key));
			createFinalGradePartCourse.setCourse(course);
			createFinalGradePartCourse.setFaculty(faculty);
			createFinalGradePartCourse.setFinalGradePart(finalGradePart);
			createFinalGradePartCourse
					.setScore(Double.parseDouble(finalGradeCourseForm.getGradeCourseScore().get(key)));

			if (finalGradePartCourse != null) {
				createFinalGradePartCourse.setId(finalGradePartCourse.getId());
			}

			saveFinalGradePartCourse(createFinalGradePartCourse);
		}
		return true;
	}

	@Override
	public FinalGradePartCourse saveFinalGradePartCourse(FinalGradePartCourse createFinalGradePartCourse) {
		return finalGradePartCourseDao.save(createFinalGradePartCourse);
	}

	@Override
	public FinalGradePart getFinalGradePart(long id) {
		return finalGradePartDao.findOne(id);
	}

	@Override
	public List<FinalGradePartCourse> findFinalGradePartCourses(Map<String, Object> map) {
		return finalGradePartCourseDao.findAll(new FinalGradePartCourseSpecification(map));
	}
	
	@Override
	public List<FinalGradePart> findFinalGradeParts(Map<String, Object> conditions) {
		return finalGradePartDao.findAll(new FinalGradePartSpecification(conditions));
	}
	
	@Override
	public List<Student> findFinalGradeStudents(Map<String, Object> conditions) {
		return studentService.findStudents(conditions);
	}
	
	@Override
	public List<FinalGradePartStudent> findFinalGradePartStudents(HashMap<String, Object> map) {
		return finalGradePartStudentDao.findAll(new FinalGradePartStudentSpecification(map));
	}
	
	@Override
	public FinalGradePartStudent getFinalGradePartStudent(Map<String, Object> map) {
		return finalGradePartStudentDao.findOne(new FinalGradePartStudentSpecification(map));
	}
	
	@Override
	public FinalGradePartCourse getFinalGradePartCourse(Long finalGradePartCourseId) {
		return finalGradePartCourseDao.findOne(finalGradePartCourseId);
	}
	
	@Override
	public FinalGradePartStudent createFinalGradePartStudent(FinalGradePartStudent finalGradePartStudent) {
		return finalGradePartStudentDao.save(finalGradePartStudent);
	}
	
	@Override
	public FinalGradePartStudent updateFinalGradePartStudent(FinalGradePartStudent finalGradePartStudent) {
		FinalGradePartStudent savedFinalGradePartStudent = finalGradePartStudentDao.findOne(finalGradePartStudent.getId());
		if (null == savedFinalGradePartStudent) {
			throw new NotFoundException("学生课程成绩不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(finalGradePartStudent, savedFinalGradePartStudent, "score");
		return finalGradePartStudentDao.save(savedFinalGradePartStudent);
	}
}
