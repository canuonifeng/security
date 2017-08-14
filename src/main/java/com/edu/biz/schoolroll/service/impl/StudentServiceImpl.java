package com.edu.biz.schoolroll.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.dao.StudentDao;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.biz.schoolroll.specification.StudentSpecification;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class StudentServiceImpl extends BaseService implements StudentService {
	@Autowired
	private StudentDao studentDao;

	@Override
	public Student createStudent(Student student) {
		return studentDao.save(student);
	}

	@Override
	public Student updateStudent(Student student) {
		Student saveStudent = studentDao.findOne(student.getId());
		if (null == saveStudent) {
			throw new NotFoundException("该学生不存在");
		}
		BeanUtils.copyPropertiesWithCopyProperties(student, saveStudent, "classroom", "no");

		return studentDao.save(student);
	}

	@Override
	public Boolean deleteStudent(Long id) {
		studentDao.delete(id);
		return null == studentDao.findOne(id);
	}

	@Override
	public Student getStudent(Long id) {
		return studentDao.findOne(id);
	}

	@Override
	public Page<Student> searchStudents(Map<String, Object> conditions, Pageable pageable) {
		return studentDao.findAll(new StudentSpecification(conditions), pageable);
	}

	@Override
	public Long countStudent(Map<String, Object> conditions) {
		return studentDao.count(new StudentSpecification(conditions));
	}

	@Override
	public List<Student> findStudents(Map<String, Object> conditions) {
		return studentDao.findAll(new StudentSpecification(conditions));
	}

	@Override
	public Boolean joinClassroom(Student student, Classroom classroom) {
		Boolean result = this.canJoinClassroom(student, classroom);
		if (!result) {
			throw new ServiceException("403", "该学生不能加入该班级");
		}
		student.setClassroom(classroom);
		updateStudent(student);
		return true;
	}

	@Override
	public Student findByClassroomIdOrderByNoDesc(Long classroomId) {
		return studentDao.findTopByClassroomIdOrderByNoDesc(classroomId);
	}

	private Boolean canJoinClassroom(Student student, Classroom classroom) {
		if (student == null) {
			return false;
		}
		if (classroom == null) {
			return false;
		}
		if (!student.getGrade().equals(classroom.getGrade())) {
			return false;
		}
		if (!student.getMajor().equals(classroom.getMajor())) {
			return false;
		}
		if (student.getClassroom() == null) {
			return true;
		}
		return false;
	}
}
