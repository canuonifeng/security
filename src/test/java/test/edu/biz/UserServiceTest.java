package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.edu.biz.dict.Gender;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.service.UserService;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("userService.data.xml")
public class UserServiceTest extends BaseServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	private User initUser() {
		User user = new User();
		user.setEmail("testuser001@test1.com");
		user.setPassword("test110");
		user.setNickname("test001");
		user.setName("test001");
		user.setUsername("testuser001");
		user.setPhone("1999999999");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);
		return userService.createUser(user);
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("test110@test1.com");
		user.setPassword("test110");
		user.setNickname("test110");
		user.setName("test110");
		user.setUsername("test110");
		user.setPhone("1999999999");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);
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
	public void testUpdateUser() {
		User initUser = initUser();
		User user = new User();
		user.setName("test002");
		user.setEmail("test002@test1.com");
		user.setNickname("test001");
		user.setName("test002");
		user.setUsername("testuser002");
		user.setPhone("13312345678");
		user.setGender(Gender.male);
		user.setStatus(UserStatus.disable);
		user.setId(initUser.getId());
		User savedUser = userService.updateUser(user);
		Assert.assertNotNull(savedUser.getId());
		Assert.assertEquals(user.getName(), savedUser.getName());
		Assert.assertNotNull(savedUser.getPassword());
		Assert.assertEquals(user.getNickname(), savedUser.getNickname());
		Assert.assertEquals(user.getUsername(), savedUser.getUsername());
		Assert.assertEquals(user.getName(), savedUser.getName());
		Assert.assertEquals(user.getPhone(), savedUser.getPhone());
		Assert.assertEquals(user.getGender(), savedUser.getGender());
		Assert.assertEquals(user.getStatus(), savedUser.getStatus());
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
