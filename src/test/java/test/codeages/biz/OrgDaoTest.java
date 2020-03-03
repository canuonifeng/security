package test.codeages.biz;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codeages.security.Application;
import com.codeages.security.biz.org.entity.Organization;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("test")
@EnableTransactionManagement
public class OrgDaoTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testOrg() {
		getOrg();
	}
	
	private Organization getOrg() {
		Organization org = new Organization();		
		Organization child = new Organization();
		child.setCode("ER_ZI");
		child.setName("咸鱼的儿子");
		entityManager.persist(child);

		List<Organization> children =  new ArrayList<>();
		children.add(child);
		org.setCode("XIAN_YU");
		org.setChildren(children);
		org.setName("会喊666的咸鱼");
		
		return org;
	}
}
