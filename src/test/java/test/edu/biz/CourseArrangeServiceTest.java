package test.edu.biz;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.teaching.service.CourseArrangeService;

public class CourseArrangeServiceTest extends BaseServiceTest {

	@Autowired
	private CourseArrangeService courseService;

	@Test
	public void testGetClassSchedule() {
		courseService.getClassSchedule("2017-2018-1", 1L, 1L);
	}
}
