package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.FacultyStatus;
import com.edu.biz.org.service.FacultyService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("facultyService.data.xml")
public class FacultyServiceTest extends BaseServiceTest {
	@Autowired
	private FacultyService facultyService;
	
	@Test
	@ExpectedDatabase(value = "facultyService.createFaculty.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateUser() {
		Faculty faculty = new Faculty();
		faculty.setCode("rjgc");
		faculty.setName("软件工程");
		facultyService.createFaculty(faculty);
	}
	
	@Test
	@ExpectedDatabase(value = "facultyService.updateFaculty.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateUser() {
		Faculty faculty = new Faculty();
		faculty.setName("测试更新土木工程系");
		faculty.setCode("cstmgc");
		faculty.setId(2L);
		facultyService.updateFaculty(faculty);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的院系()
	{
		Faculty faculty = new Faculty();
		faculty.setName("test002");
		faculty.setCode("test");
		faculty.setId(1000L);
		facultyService.updateFaculty(faculty);
	}
	
	@Test(expected=ServiceException.class)
	public void test修改代码被占用了()
	{
		Faculty faculty = new Faculty();
		faculty.setCode("jsj");
		faculty.setId(2L);
		facultyService.updateFaculty(faculty);
	}
	
	@Test
	@ExpectedDatabase(value = "facultyService.changeFacultyStatus.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testChangeFacultyStatus() {
		facultyService.changeFacultyStatus(1L, FacultyStatus.disable);
		facultyService.changeFacultyStatus(2L, FacultyStatus.enable);
	}
	
	@Test
	public void testCheckCode() {
		Assert.assertFalse(facultyService.checkCode("jsj", null));
		Assert.assertTrue(facultyService.checkCode("jsj", 1L));
		Assert.assertTrue(facultyService.checkCode("tumu",2L));
		Assert.assertTrue(facultyService.checkCode("test100",2L));
		Assert.assertTrue(facultyService.checkCode("test100",null));
	}
	
	@Test
	@ExpectedDatabase(value = "facultyService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteFaculty() {
		Assert.assertTrue(facultyService.deleteFaculty(2L));
	}
	
	@Test
	public void testGetFaculty() {
		Faculty faculty = facultyService.getFaculty(1L);
		Assert.assertEquals("计算机系", faculty.getName());
		faculty = facultyService.getFaculty(2L);
		Assert.assertEquals("土木工程系", faculty.getName());
	}
	
	@Test
	public void testGetFacultyByCode() {
		Faculty faculty = facultyService.getFacultyByCode("jsj");
		Assert.assertEquals("计算机系", faculty.getName());
		faculty = facultyService.getFacultyByCode("tumu");
		Assert.assertEquals("土木工程系", faculty.getName());
	}
}
