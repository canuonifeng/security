package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.MajorStatus;
import com.edu.biz.schoolroll.service.MajorService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("majorService.data.xml")
public class MajorServiceTest extends BaseServiceTest {
	@Autowired
	private MajorService majorService;
	
	@Test
	@ExpectedDatabase(value = "majorService.createMajor.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateMajor() {
		Major major = new Major();
		major.setCode("wlgc");
		major.setName("网络工程");
		Faculty faculty = new Faculty();
		faculty.setId(1L);
		major.setFaculty(faculty);
		majorService.createMajor(major);
	}
	
	@Test
	@ExpectedDatabase(value = "majorService.updateMajor.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateMajor() {
		Major major = new Major();
		major.setName("计科");
		major.setCode("jk");
		major.setId(1L);
		majorService.updateMajor(major);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的专业()
	{
		Major major = new Major();
		major.setName("test002");
		major.setCode("test");
		major.setId(1000L);
		majorService.updateMajor(major);
	}
	
	@Test(expected=ServiceException.class)
	public void test修改代码被占用了()
	{
		Major major = new Major();
		major.setCode("jsjkx");
		major.setId(2L);
		majorService.updateMajor(major);
	}
	
	@Test
	@ExpectedDatabase(value = "majorService.changeMajorStatus.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testChangeMajorStatus() {
		majorService.changeMajorStatus(1L, MajorStatus.disable);
		majorService.changeMajorStatus(2L, MajorStatus.enable);
	}
	
	@Test
	public void testCheckCode() {
		Assert.assertFalse(majorService.checkCode("jsjkx", null));
		Assert.assertTrue(majorService.checkCode("jsjkx", 1L));
		Assert.assertTrue(majorService.checkCode("rjgc",2L));
		Assert.assertTrue(majorService.checkCode("test100",2L));
		Assert.assertTrue(majorService.checkCode("test100",null));
	}
	
	@Test
	@ExpectedDatabase(value = "majorService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteMajor() {
		Assert.assertTrue(majorService.deleteMajor(2L));
	}
	
	@Test
	public void testGetFaculty() {
		Major major = majorService.getMajor(1L);
		Assert.assertEquals("计算机科学与技术", major.getName());
		major = majorService.getMajor(2L);
		Assert.assertEquals("软件工程", major.getName());
	}
	
	@Test
	public void testGetFacultyByCode() {
		Major major = majorService.getMajorByCode("jsjkx");
		Assert.assertEquals("计算机科学与技术", major.getName());
		major = majorService.getMajorByCode("rjgc");
		Assert.assertEquals("软件工程", major.getName());
	}
}
