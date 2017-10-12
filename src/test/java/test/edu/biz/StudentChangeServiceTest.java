package test.edu.biz;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.schoolroll.entity.ChangeStatus;
import com.edu.biz.schoolroll.entity.ChangeType;
import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentChangeLog;
import com.edu.biz.schoolroll.service.StudentChangeService;
import com.edu.core.exception.NotFoundException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("studentChangeService.data.xml")
public class StudentChangeServiceTest extends BaseServiceTest {
	@Autowired
	private StudentChangeService studentChangeService;
	
	@Test
	@ExpectedDatabase(value = "studentChangeService.createStudentChange.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateStudent() {
		StudentChange studentChange = new StudentChange();
		Student student = new Student();
		student.setId(2L);
		studentChange.setStudent(student);
		studentChange.setChangeType(ChangeType.changeMajor);
		studentChange.setStatus(ChangeStatus.facultyApproving);
		Major oldMajor = new Major();
		oldMajor.setId(1L);
		studentChange.setOldMajor(oldMajor);
		Major newMajor = new Major();
		newMajor.setId(2L);
		studentChange.setNewMajor(newMajor);
		studentChangeService.createStudentChange(studentChange);
	}
	
	@Test
	@ExpectedDatabase(value = "studentChangeService.updateStudentChange.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateChange() {
		StudentChange studentChange = new StudentChange();
		studentChange.setStatus(ChangeStatus.schoolApproving);
		Student student = new Student();
		student.setId(1L);
		studentChange.setStudent(student);
		studentChange.setChangeType(ChangeType.back);
		studentChange.setId(1L);
		studentChangeService.updateStudentChange(studentChange);
	}

	@Test(expected=NotFoundException.class)
	public void test修改不存在的异动()
	{
		StudentChange studentChange = new StudentChange();
		studentChange.setStatus(ChangeStatus.approved);;
		studentChange.setId(1000L);
		studentChangeService.updateStudentChange(studentChange);
	}
	
	@Test
	public void testGetStudent() {
		StudentChange studentChange = studentChangeService.getStudentChange(1L);
		Assert.assertEquals("back", studentChange.getChangeType().toString());
		Assert.assertEquals("schoolApproving", studentChange.getStatus().toString());
	}
	
	@Test
	public void testFindStudentChangeByStudentId() {
		List<StudentChange> changes = studentChangeService.findStudentChangeByStudentId(1L);
		Assert.assertEquals(1, changes.size());
	}
	
	@Test
	@ExpectedDatabase(value = "studentChangeService.audit.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testAudit() {
		StudentChangeLog log = new StudentChangeLog();
		StudentChange change = new StudentChange();
		Student student = new Student();
		student.setName("测试1");
		student.setId(1L);
		change.setId(1L);
		change.setStudent(student);
		change.setChangeType(ChangeType.back);
		log.setChange(change);
		log.setOldStatus(ChangeStatus.schoolApproving);
		log.setNewStatus(ChangeStatus.approved);
		studentChangeService.audit(log);
	}
}
