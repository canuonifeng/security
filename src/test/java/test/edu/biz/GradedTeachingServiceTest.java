package test.edu.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.teaching.entity.GradedTeaching;
import com.edu.biz.teaching.service.GradedTeachingService;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = "courseArrangeService.data.xml")
public class GradedTeachingServiceTest extends BaseServiceTest {

	@Autowired
	private GradedTeachingService gradedTeachingService;
	
	@Test
	public void testFindGradedTeachings() {
		Map<String, Object> conditions = new HashMap<>();
		List<GradedTeaching> gradedTeachings = gradedTeachingService.findGradedTeachings(conditions);
		Assert.assertEquals(1, gradedTeachings.size());
	}
	@Test
	public void testCheckTeachingClassroom() {
		Map<String, Object> conditions = new HashMap<>();
		Boolean result = gradedTeachingService.checkTeachingClassroom(conditions);
		Assert.assertEquals(true, result);
	}
}
