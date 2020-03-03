package test.codeages.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.codeages.security.biz.org.entity.Organization;
import com.codeages.security.biz.org.service.OrgService;

import test.codeages.BaseServiceTest;

public class OrgServiceTest extends BaseServiceTest {

	@Autowired
	private OrgService orgService;

	@Test
	public void testCreateOrg() {
		Organization org = this.getMockOrg();
		List<Organization> children = org.getChildren();
		org.setChildren(null);
		Organization savedOrg = orgService.createOrg(org);

		for (int i = 0; i < children.size(); i++) {
			Organization child = children.get(i);
			child.setParent(savedOrg);
			child = orgService.createOrg(child);
			children.set(i, child);
			System.out.println("child:" + child.getId());
		}

		savedOrg.setChildren(children);
//////////////
		Assert.assertNotNull(savedOrg.getId());
		Assert.assertEquals(org.getCode(), savedOrg.getCode());
		Assert.assertEquals(org.getName(), savedOrg.getName());
		Assert.assertNotNull(savedOrg.getChildren());

		List<Organization> orgs = orgService.findOrgs(new HashMap());
		for (Organization org1 : orgs) {
			if (org1.getParent() == null) {
				System.out.println("id: " + org1.getId());
			} else {
				System.out.println("id: " + org1.getId() + ", parent_id:" + org1.getParent().getId());
			}
		}

		for (int i = 0; i < children.size(); i++) {
			Organization child = children.get(i);
			orgService.deleteOrg(child.getId());
		}
		orgService.deleteOrg(org.getId());
	}
	//
	// @Test
	// @Transactional
	// @Rollback
	// public void testUpdateOrg() {
	// Organization org = this.getOrg();
	// Organization savedOrg = orgService.createOrg(org);
	//
	// Organization updateOrg = new Organization();
	// updateOrg.setId(savedOrg.getId());
	// updateOrg.setCode("xian_yu");
	// updateOrg.setName("LAKERS");
	// Organization result = orgService.updateOrg(updateOrg);
	//
	// Assert.assertEquals(org.getCode(), result.getCode());
	// Assert.assertEquals(org.getName(), result.getName());
	// }
	//
	// @Test
	// @Transactional
	// @Rollback
	// public void testDelete() {
	// Organization org = this.getOrg();
	// Organization savedOrg = orgService.createOrg(org);
	// boolean result = orgService.deleteOrg(savedOrg.getId());
	// Organization org1 = orgService.getOrg(savedOrg.getId());
	// Assert.assertNotNull(org1);
	// }
	//
	// @Test
	// @Transactional
	// @Rollback
	// public void testGet() {
	// Organization org = this.getOrg();
	// Organization savedOrg = orgService.createOrg(org);
	// Organization getOrg = orgService.getOrg(savedOrg.getId());
	//
	// Assert.assertNotNull(getOrg.getId());
	// Assert.assertEquals(org.getCode(), getOrg.getCode());
	// Assert.assertEquals(org.getName(), getOrg.getName());
	// }

	private Organization getMockOrg() {
		Organization child = new Organization();
		child.setCode("ER_ZI");
		child.setName("咸鱼的儿子");

		List<Organization> children = new ArrayList<Organization>();
		children.add(child);

		Organization org = new Organization();
		org.setCode("XIAN_YU");
		org.setName("会喊666的咸鱼");
		org.setChildren(children);

		return org;
	}
}
