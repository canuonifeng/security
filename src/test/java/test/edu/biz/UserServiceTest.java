package test.edu.biz;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.edu.biz.security.entity.Gender;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.service.UserService;

public class UserServiceTest extends BaseServiceTest{
	
	@Autowired
	private UserService userService;
	
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
}
