package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.dict.Gender;
import com.edu.biz.dict.IDType;
import com.edu.biz.dict.Nation;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentOrigin;
import com.edu.biz.schoolroll.entity.StudentStatus;
import com.edu.biz.schoolroll.service.ClassroomService;
import com.edu.biz.schoolroll.service.StudentService;
import com.edu.core.exception.NotFoundException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("studentService.data.xml")
public class StudentServiceTest extends BaseServiceTest {
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassroomService classroomService;
	
	@Test
	@ExpectedDatabase(value = "studentService.createStudent.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateStudent() {
		Student student = new Student();
		student.setName("test1");
		student.setGender(Gender.male);
		student.setAdmissionTime("2013-09");
		student.setGrade("2013");
		student.setStatus(StudentStatus.enable);
		student.setOrigin(StudentOrigin.unified);
		student.setNativePlace("杭州");
		student.setIdcard("111111");
		student.setBirthday("1995-01-01");
		student.setNation(Nation.HAN);
		student.setIdtype(IDType.idcard);
		studentService.createStudent(student);
	}
	
	@Test
	@ExpectedDatabase(value = "studentService.updateStudent.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateStudent() {
		Student student = new Student();
		Classroom classroom = classroomService.getClassroom(1L);
		student.setClassroom(classroom);
		student.setId(1L);
		studentService.updateStudent(student);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的学生()
	{
		Student student = new Student();
		student.setName("test002");
		student.setId(1000L);
		studentService.updateStudent(student);
	}
	
	@Test
	@ExpectedDatabase(value = "studentService.changeStudentStatus.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testChangeStudentStatus() {
		Student student = studentService.getStudent(1L);
		student.setStatus(StudentStatus.pause);
		studentService.changeStudentStatus(student);
	}
	
	@Test
	@ExpectedDatabase(value = "studentService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteStudent() {
		Assert.assertTrue(studentService.deleteStudent(2L));
	}
	
	@Test
	public void testGetStudent() {
		Student student = studentService.getStudent(1L);
		Assert.assertEquals("测试1", student.getName());
		student = studentService.getStudent(2L);
		Assert.assertEquals("测试1", student.getName());
	}
	
	@Test
	public void testCountStudent() {
		Long count = studentService.countStudent(null);
		Assert.assertEquals("2", count.toString());
	}
	
	@Test
	public void testJoinClassroom() {
		Student student = studentService.getStudent(1L);
		Classroom classroom = classroomService.getClassroom(1L);
		Assert.assertTrue(studentService.joinClassroom(student, classroom));
	}
	
	@Test
	@ExpectedDatabase(value = "studentService.assignStudentNum.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testAssignStudentNum() {
		Student student = studentService.getStudent(1L);
		Classroom classroom = classroomService.getClassroom(1L);
		student.setClassroom(classroom);
		student.setNo(classroom.getCode()+classroom.getLastAssignNum());
		studentService.AssignStudentNum(student);
	}
}
