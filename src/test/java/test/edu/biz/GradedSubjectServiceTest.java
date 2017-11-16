package test.edu.biz;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;
import com.edu.biz.teaching.service.GradedSubjectService;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("gradedSubjectService.data.xml")
public class GradedSubjectServiceTest extends BaseServiceTest{
	
	@Autowired
	private GradedSubjectService gradedSubjectService;
	
	@Test
	@ExpectedDatabase(value = "gradedSubjectService.createGradedSubject.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateGradedSubject() {
		GradedSubject gradedSubject = new GradedSubject();
		Faculty faculty = new Faculty();
		faculty.setId(2L);
		gradedSubject.setFaculty(faculty);
		gradedSubject.setGrade("2017");
		gradedSubject.setName("咸鱼");
		gradedSubjectService.createGradedSubject(gradedSubject);
	}
	@Test
	@ExpectedDatabase(value = "gradedSubjectService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testdeleteGradedSubject() {
		gradedSubjectService.deleteGradedSubject(4L);
	}
	@Test
	@ExpectedDatabase(value = "gradedSubjectService.createGradedSubjectResult.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testcreateResult() {
		GradedSubjectResult gradedSubjectResult = new GradedSubjectResult();
		GradedSubject gradedSubject = new GradedSubject();
		gradedSubject.setId(1L);
		Student student = new Student();
		student.setId(1L);
		gradedSubjectResult.setGradedSubject(gradedSubject);
		gradedSubjectResult.setScore(2.0);
		gradedSubjectResult.setStudent(student);
		gradedSubjectService.createResult(gradedSubjectResult);
	}
	@Test
	@ExpectedDatabase(value = "gradedSubjectService.updateGradedSubjectResult.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testupdateResult() {
		GradedSubjectResult gradedSubjectResult = new GradedSubjectResult();
		gradedSubjectResult.setId(1L);
		GradedSubject gradedSubject = new GradedSubject();
		gradedSubject.setId(1L);
		Student student = new Student();
		student.setId(1L);
		gradedSubjectResult.setGradedSubject(gradedSubject);
		gradedSubjectResult.setScore(2.0);
		gradedSubjectResult.setStudent(student);
		gradedSubjectService.updateResult(gradedSubjectResult);
	}
	@Test
	public void testfindGradedSubjects() {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("grade", 2017);
		conditions.put("facultyId", 1L);
		List<GradedSubject> gradedSubjects = gradedSubjectService.findGradedSubjects(conditions);
		Assert.assertEquals(4, gradedSubjects.size());
	}
	@Test
	public void testfindGradedSubjectResults() {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("grade", 2017);
		conditions.put("facultyId", 1L);
		conditions.put("gradedSubjectId", 1L);
		List<GradedSubjectResult> gradedSubjectResults = gradedSubjectService.findGradedSubjectResults(conditions);
		Assert.assertEquals(1, gradedSubjectResults.size());
	}
	@Test
	public void testgetGradedSubjectResult() {
		Map<String, Object> map = new HashMap<>();
		map.put("studentId", 1L);
		map.put("gradedSubjectId", 1L);
		GradedSubjectResult gradedSubjectResult = gradedSubjectService.getGradedSubjectResult(map);
		Assert.assertEquals("12.0", gradedSubjectResult.getScore().toString());
	}
}
