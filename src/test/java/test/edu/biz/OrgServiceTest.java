package test.edu.biz;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.org.entity.Organization;
import com.edu.biz.org.service.OrgService;

public class OrgServiceTest extends BaseServiceTest{
	
	@Autowired
	private OrgService orgService;
	
	@Test
	public void testCreateOrg() {
		Organization org = this.getOrg();
		Organization savedOrg = orgService.createOrg(org);
		
		Assert.assertNotNull(savedOrg.getId());
		Assert.assertEquals(org.getCode(), savedOrg.getCode());
		Assert.assertEquals(org.getName(), savedOrg.getName());
		Assert.assertNotNull(savedOrg.getChildren());
	}
	
	@Test
	public void testUpdateOrg() {
		Organization org = this.getOrg();
		Organization savedOrg = orgService.createOrg(org);
		
		Organization updateOrg = new Organization();
		updateOrg.setId(savedOrg.getId());
		updateOrg.setCode("xian_yu");
		updateOrg.setName("LAKERS");
		Organization result = orgService.updateOrg(updateOrg);

		Assert.assertEquals(org.getCode(), result.getCode());
		Assert.assertEquals(org.getName(), result.getName());
	}
	
	@Test
	public void testDelete() {
		Organization org = this.getOrg();
		Organization savedOrg = orgService.createOrg(org);
		boolean result = orgService.deleteOrg(savedOrg.getId());
		Organization org1 = orgService.getOrg(savedOrg.getId());
		Assert.assertNotNull(org1);
	}
	
	@Test
	public void testGet() {
		Organization org = this.getOrg();
		Organization savedOrg = orgService.createOrg(org);
		Organization getOrg = orgService.getOrg(savedOrg.getId());

		Assert.assertNotNull(getOrg.getId());
		Assert.assertEquals(org.getCode(), getOrg.getCode());
		Assert.assertEquals(org.getName(), getOrg.getName());
	}
	
	private Organization getOrg() {
		Organization org = new Organization();		
		Organization child = new Organization();
		child.setCode("ER_ZI");
		child.setName("咸鱼的儿子");
		

		List<Organization> children =  new ArrayList<>();
		children.add(child);
		org.setCode("XIAN_YU");
		org.setChildren(children);
		org.setName("会喊666的咸鱼");
		
		return org;
	}
}
