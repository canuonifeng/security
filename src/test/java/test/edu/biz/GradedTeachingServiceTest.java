package test.edu.biz;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teachingres.entity.Course;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup(value = "gradedTeachingService.data.xml")
@Transactional
public class GradedTeachingServiceTest extends BaseServiceTest {

	@Autowired
	private GradedTeachingService gradedTeachingService;
	
	@Test
	@ExpectedDatabase(value = "gradedTeachingService.createGraded.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateGraded() {
		GradedTeaching teaching = new GradedTeaching();
		Course course = new Course();
		course.setId(1L);
//		Classroom classroom = new Classroom();
//		classroom.setId(1L);
//		List<Classroom> classrooms = new ArrayList<>();
//		classrooms.add(classroom);
//		teaching.setClassrooms(classrooms);
		teaching.setCourse(course);
		teaching.setSchooltime("1-15");
		gradedTeachingService.createGraded(teaching);
	}
	
	@Test
	@ExpectedDatabase(value = "gradedTeachingService.createSchooltime.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateSchooltime() {
		GradedSchooltime time = new GradedSchooltime();
		GradedTeaching teaching = new GradedTeaching();
		teaching.setId(1L);
		time.setGradedTeaching(teaching);
		time.setWeek(1);
		time.setTimeSlot(1);
		time.setPeriod(1);
		List<GradedSchooltime> list = new ArrayList<>();
		list.add(time);
		gradedTeachingService.createSchooltimes(list);
	}
	
	@Test
	@ExpectedDatabase(value = "gradedTeachingService.createRank.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateRank() {
		GradedRank rank = new GradedRank();
		GradedTeaching teaching = new GradedTeaching();
		teaching.setId(1L);
		rank.setName("A");
		rank.setGradedTeaching(teaching);
		rank.setMinScore("0");
		rank.setMaxScore("100");
		List<GradedRank> list = new ArrayList<>();
		list.add(rank);
		gradedTeachingService.createRank(list);
	}
	
//	@Test
//	public void testFindGradedTeachings() {
//		
//	}
//	@Test
//	public void testFindGradedTeachingCourses() {
//		
//	}
//	@Test
//	public void testCheckTeachingTime() {
//		
//	}
//	@Test
//	public void testCheckTeachingClassroom() {
//		
//	}
}
