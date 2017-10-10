package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.teaching.entity.Term;
import com.edu.biz.teaching.service.TermService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("termService.data.xml")
public class TermServiceTest extends BaseServiceTest {
	@Autowired
	private TermService termService;

	@Test
	@ExpectedDatabase(value = "termService.createTerm.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateTerm() {
		Term term = new Term();
		term.setTitle("2012-2013第二学期");
		term.setCode("12-13-2");
		term.setLongCode("2012-2013-2");
		term.setStartDate("2013-02-20");
		term.setEndDate("2013-06-20");
		term.setCurrent(0);
		termService.createTerm(term);
	}
	
	@Test
	@ExpectedDatabase(value = "termService.updateTerm.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateTerm() {
		Term term = new Term();
		term.setTitle("2013-2014第一学期");
		term.setCode("13-14-1");
		term.setLongCode("2013-2014-1");
		term.setStartDate("2013-09-01");
		term.setEndDate("2014-02-20");
		term.setCurrent(0);
		term.setId(3L);
		termService.updateTerm(term);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的院系()
	{
		Term term = new Term();
		term.setTitle("test002");
		term.setCode("test");
		term.setId(1000L);
		termService.updateTerm(term);
	}
	
	@Test(expected=ServiceException.class)
	public void test修改代码被占用了()
	{
		Term term = new Term();
		term.setCode("11-12-1");
		term.setId(2L);
		termService.updateTerm(term);
	}
	
	@Test
	@ExpectedDatabase(value = "termService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteTerm() {
		Assert.assertTrue(termService.deleteTerm(2L));
	}
	
	@Test
	@ExpectedDatabase(value = "termService.changeTermCurrent.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testChangeTermCurrent() {
		termService.changeTermCurrent(1L, 1);
	}
	
	@Test
	public void testCheckCode() {
		Assert.assertFalse(termService.checkCode("11-12-1", null));
		Assert.assertTrue(termService.checkCode("11-12-1", 1L));
		Assert.assertTrue(termService.checkCode("11-12-2",2L));
		Assert.assertTrue(termService.checkCode("test100",2L));
		Assert.assertTrue(termService.checkCode("test100",null));
	}
	
	@Test
	public void testGetTerm() {
		Term term = termService.getTerm(1L);
		Assert.assertEquals("2011-2012第一学期", term.getTitle());
		term = termService.getTerm(2L);
		Assert.assertEquals("2011-2012第二学期", term.getTitle());
	}
}
