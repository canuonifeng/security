package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.security.entity.Gender;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.service.UserService;

public class UserServiceTest extends BaseServiceTest {

	@Autowired
	private UserService userService;

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

	@Test
	public void testChecUserStatus() {
		User initUser = initUser();
		User user = userService.changeUserStatus(initUser.getId(), UserStatus.disable);
		Assert.assertEquals(user.getStatus(), UserStatus.disable);
		Assert.assertNotNull(user.getPassword());
		Assert.assertNotEquals(user.getStatus(), UserStatus.enable);
	}

	@Test
	public void testCheckUserName() {
		User initUser = initUser();
		Assert.assertFalse(userService.checkUserName("testuser001", null));
		Assert.assertTrue(userService.checkUserName("testuser001", initUser.getId()));
		Assert.assertTrue(userService.checkUserName("testuser002", initUser.getId()));
	}

	@Test
	public void testDeleteUser() {
		User initUser = initUser();
		Assert.assertFalse(userService.checkUserName("testuser001", null));
		userService.deleteUser(initUser.getId());
		Assert.assertTrue(userService.checkUserName("testuser001", null));
	}
	
	public void testGetUser(){
		User initUser = initUser();
		User user = userService.getUserById(initUser.getId());
		Assert.assertEquals(initUser,user);
	}
}
