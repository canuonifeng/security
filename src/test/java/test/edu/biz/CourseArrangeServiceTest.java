package test.edu.biz;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.teaching.entity.ClassSchedule;
import com.edu.biz.teaching.entity.ScheduleCycle;
import com.edu.biz.teaching.entity.ScheduleTeacher;
import com.edu.biz.teaching.service.CourseArrangeService;
import com.edu.biz.teachingres.entity.BuildingRoom;
import com.edu.biz.teachingres.entity.Course;
import com.edu.biz.teachingres.entity.Teacher;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup(value = "courseArrangeService.data.xml")
public class CourseArrangeServiceTest extends BaseServiceTest {

	@Autowired
	private CourseArrangeService courseArrangeService;

	@Test
	public void testGetClassSchedule() {
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule("17-18-1", 1L, 1L);
		Assert.assertEquals("17-18-1", classSchedule.getTerm());
	}
	
//	@Test
//	@ExpectedDatabase(value = "courseArrangeService.createClassSchedule.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
//	public void testCreateClassSchedule() {
//		ClassSchedule classSchedule = new ClassSchedule();
//		classSchedule.setId(3L);
//		classSchedule.setTerm("17-18-1");
//		Course course = new Course();
//		course.setId(3L);
//		classSchedule.setCourse(course);
//		ClassSchedule savedClassSchedule = courseArrangeService.createClassSchedule(classSchedule);
//		Assert.assertNotNull(savedClassSchedule.getId());
//		Assert.assertEquals(classSchedule.getTerm(), savedClassSchedule.getTerm());
//	}
//	
//	@Test
//	@ExpectedDatabase(value = "courseArrangeService.createScheduleCycle.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
//	public void testCreateScheduleCycle() {
//		ScheduleCycle scheduleCycle = new ScheduleCycle();
//		BuildingRoom buildingRoom =  new BuildingRoom();
//		buildingRoom.setId(1L);
//		scheduleCycle.setBuildingRoom(buildingRoom);
//		ClassSchedule classSchedule = new ClassSchedule();
//		classSchedule.setId(1L);
//		scheduleCycle.setClassSchedule(classSchedule);
//		scheduleCycle.setPeriod("1-1");
//		scheduleCycle.setWeek(1);
//		ScheduleCycle savedScheduleCycle = courseArrangeService.createScheduleCycle(scheduleCycle);
//		Assert.assertNotNull(savedScheduleCycle.getId());
//		Assert.assertEquals(savedScheduleCycle.getPeriod(), scheduleCycle.getPeriod());
//		Assert.assertEquals(savedScheduleCycle.getWeek(), scheduleCycle.getWeek());
//	}
//	
//	@Test
//	public void testGetCourseArrange() {
////		courseService.getClassSchedule("17-18-1", 1L, 1L);
//	}
//	
//	@Test
//	@ExpectedDatabase(value = "courseArrangeService.deleteScheduleCycle.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
//	public void testDeleteScheduleCycle() {
//		Assert.assertTrue(courseArrangeService.deleteScheduleCycle(1L));
//	}
//	
//	@Test
//	public void testGetScheduleCycle() {
//		ScheduleCycle scheduleCycle =  courseArrangeService.getScheduleCycle( 1L);
//		Assert.assertEquals("1", scheduleCycle.getWeek());
//		Assert.assertEquals("1-1", scheduleCycle.getPeriod());
//	}
//	
//	@Test
//	public void testFindScheduleCycles() {
////		courseService.getClassSchedule("17-18-1", 1L, 1L);
//	}
//	
//	@Test
//	@ExpectedDatabase(value = "courseArrangeService.deleteClassSchedule.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
//	public void testDeleteClassSchedule() {
//		Assert.assertTrue(courseArrangeService.deleteClassSchedule(1L));
//	}
//	
//	@Test
//	public void testgetClassSchedule() {
//		ClassSchedule classSchedule =  courseArrangeService.getClassSchedule( 1L);
//		Assert.assertEquals("17-18-1", classSchedule.getTerm());
//	}
//	
//	@Test
//	public void testCountScheduleCyle() {
//		Map<String, Object> map = new HashMap<>();
//		Long count = courseArrangeService.countScheduleCyle(map);
//		Assert.assertEquals("2", count.toString());
//	}
//	
//	@Test
//	@ExpectedDatabase(value = "courseArrangeService.createScheduleTeacher.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
//		public void testCreateScheduleTeacher() {
//		ScheduleTeacher scheduleTeacher = new ScheduleTeacher();
//		Teacher teacher = new Teacher();
//		teacher.setId(1L);
//		ClassSchedule classSchedule = new ClassSchedule();
//		classSchedule.setId(1L);
//		scheduleTeacher.setClassSchedule(classSchedule);
//		scheduleTeacher.setTeacher(teacher);
//		scheduleTeacher.setMaster(1);
//		ScheduleTeacher savedScheduleTeacher = courseArrangeService.createScheduleTeacher(scheduleTeacher);
//		Assert.assertNotNull(savedScheduleTeacher.getId());
//		Assert.assertEquals(savedScheduleTeacher.getMaster(), scheduleTeacher.getMaster());
//	}
//	
//	@Test
//	public void testFindScheduleTeachers() {
////		courseService.getClassSchedule("17-18-1", 1L, 1L);
//	}
}
