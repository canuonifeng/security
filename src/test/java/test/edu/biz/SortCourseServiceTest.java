package test.edu.biz;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.teaching.service.SortCourseService;

public class SortCourseServiceTest extends BaseServiceTest {

	@Autowired
	private SortCourseService courseService;
	
	@Test
	public void testGetClassSchedule() {
		courseService.getClassSchedule("2017-2018-1", 1L, 1L);
	}
}
