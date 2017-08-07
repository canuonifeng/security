package test.edu.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.edu.biz.security.entity.Gender;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.service.UserService;
import com.edu.core.ResponseWrapper;

public class UserControllerTest extends BaseControllerTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testAddUser() {
		User user = new User();
		user.setEmail("test10@test1.com");
		user.setPassword("test10");
		user.setNickname("test10");
		user.setName("test10");
		user.setUsername("test10");
		user.setPhone("1999999999");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);

		// 拿到header信息
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-auth-token", authToken);
		HttpEntity requestEntity = new HttpEntity(user, headers);

		ResponseWrapper savedUser = restTemplate.postForObject("/api/user", requestEntity, ResponseWrapper.class);
		if (savedUser.getBody()  instanceof Map) {
			Map map = (Map) savedUser.getBody();
			Assert.assertEquals(user.getEmail(), map.get("email").toString());
		} else {
			throw new RuntimeException();
		}
	}

	@Test
	public void testGetUser() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-auth-token", authToken);
		HttpEntity requestEntity = new HttpEntity(new HashMap(), headers);

		ResponseEntity<ResponseWrapper<Map>> response = restTemplate.exchange("/api/user/1", HttpMethod.GET,
				requestEntity, ResponseWrapper.class, new HashMap());
		ResponseWrapper<Map> wrapperUser = response.getBody();
		Map user = wrapperUser.getBody();
		Assert.assertNotNull(user);
	}
}
