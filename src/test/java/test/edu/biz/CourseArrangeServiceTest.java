package test.edu.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.common.dao.service.SettingService;
import com.edu.biz.common.entity.Setting;
import com.edu.biz.schoolroll.entity.Classroom;
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
	@Autowired
	private SettingService settingService;
	
	@Test
	public void testGetClassSchedule() {
		ClassSchedule classSchedule = courseArrangeService.getClassSchedule("17-18-1", 1L, 1L);
		Assert.assertEquals("17-18-1", classSchedule.getTerm());
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.createClassSchedule.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateClassSchedule() {
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setTerm("17-18-1");
		Course course = new Course();
		course.setId(2L);
		classSchedule.setCourse(course);
		ClassSchedule savedClassSchedule = courseArrangeService.createClassSchedule(classSchedule);
		Assert.assertNotNull(savedClassSchedule.getId());
		Assert.assertEquals(classSchedule.getTerm(), savedClassSchedule.getTerm());
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.createScheduleCycle.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateScheduleCycle() {
		ScheduleCycle scheduleCycle = new ScheduleCycle();
		BuildingRoom buildingRoom =  new BuildingRoom();
		buildingRoom.setId(1L);
		scheduleCycle.setBuildingRoom(buildingRoom);
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setId(1L);
		scheduleCycle.setClassSchedule(classSchedule);
		scheduleCycle.setPeriod("1-2");
		scheduleCycle.setWeek(2);
		ScheduleCycle savedScheduleCycle = courseArrangeService.createScheduleCycle(scheduleCycle);
		Assert.assertNotNull(savedScheduleCycle.getId());
		Assert.assertEquals(savedScheduleCycle.getPeriod(), scheduleCycle.getPeriod());
		Assert.assertEquals(savedScheduleCycle.getWeek(), scheduleCycle.getWeek());
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.deleteScheduleCycle.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteScheduleCycle() {
		Assert.assertTrue(courseArrangeService.deleteScheduleCycle(1L));
	}
	
	@Test
	public void testGetScheduleCycle() {
		ScheduleCycle scheduleCycle =  courseArrangeService.getScheduleCycle( 1L);
		Assert.assertEquals(1L, scheduleCycle.getWeek());
		Assert.assertEquals("1-1", scheduleCycle.getPeriod());
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.deleteClassSchedule.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteClassSchedule() {
		Assert.assertTrue(courseArrangeService.deleteClassSchedule(1L));
	}
	
	@Test
	public void testgetClassSchedule() {
		ClassSchedule classSchedule =  courseArrangeService.getClassSchedule( 1L);
		Assert.assertEquals("17-18-1", classSchedule.getTerm());
	}
	
	@Test
	public void testCountScheduleCyle() {
		Map<String, Object> map = new HashMap<>();
		Long count = courseArrangeService.countScheduleCyle(map);
		Assert.assertEquals("2", count.toString());
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.createScheduleTeacher.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
		public void testCreateScheduleTeacher() {
		ScheduleTeacher scheduleTeacher = new ScheduleTeacher();
		Teacher teacher = new Teacher();
		teacher.setId(1L);
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setId(1L);
		scheduleTeacher.setClassSchedule(classSchedule);
		scheduleTeacher.setTeacher(teacher);
		scheduleTeacher.setMaster(1);
		ScheduleTeacher savedScheduleTeacher = courseArrangeService.createScheduleTeacher(scheduleTeacher);
		Assert.assertNotNull(savedScheduleTeacher.getId());
		Assert.assertEquals(savedScheduleTeacher.getMaster(), scheduleTeacher.getMaster());
	}

	@Test
	public void testFindScheduleCycles() {
		Map<String, Object> map = new HashMap<>();
		map.put("scheduleId", 1L);
		List<ScheduleCycle> scheduleCycles = courseArrangeService.findScheduleCycles(map);
		Assert.assertEquals(1,scheduleCycles.size());
	}
	
	@Test
	public void testGetCourseArrange() {
		Setting setting = new Setting();
		setting.setId(1L);
		setting.setCode("course_arrange_limit");
		setting.setValue("{\"week\":[1,2,3,4,5],\"morning\":\"2\",\"afternoon\":\"4\",\"night\":\"1\"}");
		settingService.modifySetting(setting);
		Map<Integer, Map<String, Object>> result = courseArrangeService.getCourseArrange("17-18-1", 1L);
		Assert.assertNotEquals(null, result.get(1).get("1-1"));
	}
	
	@Test
	public void testFindScheduleTeachers() {
		Map<String, Object> map = new HashMap<>();
		map.put("scheduleId", 1L);
		List<ScheduleTeacher> scheduleTeachers = courseArrangeService.findScheduleTeachers(map);
		Assert.assertEquals(1,scheduleTeachers.size());
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.updateClasssSchedule.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateClassSchedule() {
		Classroom classroom = new Classroom();
		classroom.setId(2L);
		List<Classroom> classrooms = new ArrayList<>();
		classrooms.add(classroom);
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setId(1L);
		classSchedule.setClassrooms(classrooms);
		courseArrangeService.updateClassSchedule(classSchedule);
	}
	
	@Test
	@ExpectedDatabase(value = "courseArrangeService.deleteScheduleTeacher.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteScheduleTeacherByScheduleId() {
		Assert.assertTrue(courseArrangeService.deleteScheduleTeacherByScheduleId(1L));
	}
	
	@Test
	public void testGetMasterScheduleTeacher() {
		ScheduleTeacher scheduleTeacher  =  courseArrangeService.getMasterScheduleTeacher(1L);
		Assert.assertEquals("1", scheduleTeacher.getTeacher().getId().toString());
	}
}
