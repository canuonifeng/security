package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.dict.Gender;
import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.entity.TeacherStatus;
import com.edu.biz.teachingres.service.TeacherService;
import com.edu.core.exception.NotFoundException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("teacherService.data.xml")
public class TeacherServiceTest extends BaseServiceTest {
	@Autowired
	TeacherService teacherService;
	
	@Test
	@ExpectedDatabase(value = "teacherService.createTeacher.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("教师4");
		teacher.setNo("004");
		teacher.setGender(Gender.female);
		teacher.setStartWorkTime("2015-05");
		teacher.setStatus(TeacherStatus.enable);
		teacherService.createTeacher(teacher);
	}
	
	@Test
	@ExpectedDatabase(value = "teacherService.updateTeacher.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("测试教师");
		teacher.setNo("000");
		teacher.setGender(Gender.female);
		teacher.setStartWorkTime("2015-05");
		teacher.setStatus(TeacherStatus.enable);
		teacher.setId(2L);
		teacherService.updateTeacher(teacher);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的教师()
	{
		Teacher teacher = new Teacher();
		teacher.setName("test002");
		teacher.setId(1000L);
		teacherService.updateTeacher(teacher);
	}
	
	@Test
	@ExpectedDatabase(value = "teacherService.changeTeacherStatus.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testChangeTeacherStatus() {
		teacherService.changeTeacherStatus(1L, TeacherStatus.learnOut);
		teacherService.changeTeacherStatus(2L, TeacherStatus.vacation);
	}
	
	@Test
	@ExpectedDatabase(value = "teacherService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteTeacher() {
		Assert.assertTrue(teacherService.deleteTeacher(2L));
	}
	
	@Test
	public void testGetTeacher() {
		Teacher teacher = teacherService.getTeacher(1L);
		Assert.assertEquals("教师1", teacher.getName());
		teacher = teacherService.getTeacher(2L);
		Assert.assertEquals("教师2", teacher.getName());
	}
}
