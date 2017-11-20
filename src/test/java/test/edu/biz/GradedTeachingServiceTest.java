package test.edu.biz;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.edu.biz.teaching.entity.GradedCourse;
import com.edu.biz.teaching.entity.GradedCourseAndCourseTime;
import com.edu.biz.teaching.entity.GradedCourseSchooltime;
import com.edu.biz.teaching.entity.GradedRank;
import com.edu.biz.teaching.entity.GradedSchooltime;
import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.Teacher;
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
		time.setPeriod(2);
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
		rank.setName("B");
		rank.setGradedTeaching(teaching);
		rank.setMinScore("60");
		rank.setMaxScore("80");
		List<GradedRank> list = new ArrayList<>();
		list.add(rank);
		gradedTeachingService.createRank(list);
	}
	
	@Test
	@ExpectedDatabase(value = "gradedTeachingService.createCourse.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateCourse() {
		List<GradedCourseAndCourseTime> list = new ArrayList<>();
		GradedCourseAndCourseTime courseAndCourseTime =  new GradedCourseAndCourseTime();
		
		GradedCourse course = new GradedCourse();
		GradedTeaching teaching = new GradedTeaching();
		teaching.setId(1L);
		GradedRank rank = new GradedRank();
		rank.setId(1L);
		Teacher teacher = new Teacher();
		teacher.setId(1L);
		course.setGradedRank(rank);
		course.setGradedTeaching(teaching);
		course.setTeacher(teacher);
		course.setStudentNumber(50);
		courseAndCourseTime.setGradedCourse(course);
		
		List<GradedCourseSchooltime> courseTimes = new ArrayList<>();
		GradedCourseSchooltime courseTime = new GradedCourseSchooltime();
		GradedSchooltime time = new GradedSchooltime();
		time.setId(1L);
		BuildingRoom room = new BuildingRoom();
		room.setId(1L);
		courseTime.setBuildingRoom(room);
		courseTime.setGradedSchooltime(time);
		courseTimes.add(courseTime);
		
		courseAndCourseTime.setGradedCourseTime(courseTimes);
		list.add(courseAndCourseTime);
		
		gradedTeachingService.createCourse(list);
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
