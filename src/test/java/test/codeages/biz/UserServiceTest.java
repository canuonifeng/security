package test.codeages.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.codeages.framework.exception.NotFoundException;
import com.codeages.framework.exception.ServiceException;
import com.codeages.security.biz.dict.Gender;
import com.codeages.security.biz.org.entity.Organization;
import com.codeages.security.biz.security.entity.User;
import com.codeages.security.biz.security.entity.UserStatus;
import com.codeages.security.biz.security.service.UserService;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import test.codeages.BaseServiceTest;

@DatabaseSetup("userService.data.xml")
public class UserServiceTest extends BaseServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Test
	@ExpectedDatabase(value = "userService.createUser.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateUser() {
		User user = new User();
		user.setEmail("test5@edusoho.com");
		user.setPassword("test110");
		user.setNickname("新用户");
		user.setName("新用户");
		user.setUsername("test5");
		user.setPhone("13212345678");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);
		Organization org = new Organization();
		org.setId(3L);
		user.setOrg(org);
		User savedUser = userService.createUser(user);

		Assert.assertNotNull(savedUser.getId());
		Assert.assertEquals(user.getEmail(), savedUser.getEmail());
		Assert.assertNotNull(savedUser.getPassword());
		Assert.assertEquals(user.getNickname(), savedUser.getNickname());
		Assert.assertEquals(user.getUsername(), savedUser.getUsername());
		Assert.assertEquals(user.getName(), savedUser.getName());
		Assert.assertEquals(user.getPhone(), savedUser.getPhone());
		Assert.assertEquals(user.getGender(), savedUser.getGender());
		Assert.assertEquals(user.getStatus(), savedUser.getStatus());
	}

	@Test
	@ExpectedDatabase(value = "userService.updateUser.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testUpdateUser() {
		User user = new User();
		user.setName("张三");
		user.setEmail("13312345678");
		user.setNickname("张三的昵称");
		user.setUsername("test1");
		user.setPhone("13312345678");
		user.setGender(Gender.female);
		user.setEmail("zhangsan@edusoho.com");
		user.setId(2L);
		userService.updateUser(user);
	}
	
	@Test(expected=NotFoundException.class)
	public void test修改不存在的用户()
	{
		User user = new User();
		user.setName("test002");
		user.setEmail("test002@test1.com");
		user.setId(1000L);
		userService.updateUser(user);
	}
	
	@Test(expected=ServiceException.class)
	public void test修改用户用户名被占用了()
	{
		User user = new User();
		user.setName("test002");
		user.setEmail("test002@test1.com");
		user.setUsername("admin");
		user.setId(5L);
		userService.updateUser(user);
	}

	@Test
	@ExpectedDatabase(value = "userService.changeUserStatus.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testChangeUserStatus() {
		userService.changeUserStatus(2L, UserStatus.disable);
		userService.changeUserStatus(5L, UserStatus.enable);
	}

	@Test
	public void testCheckUserName() {
		Assert.assertFalse(userService.checkUserName("admin", null));
		Assert.assertTrue(userService.checkUserName("admin", 1L));
		Assert.assertTrue(userService.checkUserName("test1",2L));
		Assert.assertTrue(userService.checkUserName("test100",2L));
		Assert.assertTrue(userService.checkUserName("test100",null));
	}

	@Test
	@ExpectedDatabase(value = "userService.delete.expectedData.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteUser() {
		Assert.assertTrue(userService.deleteUser(2L));
		Assert.assertTrue(userService.deleteUser(3L));
	}

	@Test
	public void testGetUser() {
		User user = userService.getUserById(1L);
		Assert.assertEquals("admin", user.getUsername());
		user = userService.getUserById(2L);
		Assert.assertEquals("test1", user.getUsername());
	}

	@Test
	public void testLoadUserByUsername() {
		UserDetails user = userDetailsService.loadUserByUsername("admin");
		Assert.assertEquals("admin", user.getUsername());
	}
}
