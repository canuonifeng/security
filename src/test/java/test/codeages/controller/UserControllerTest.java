package test.codeages.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.codeages.biz.dict.Gender;
import com.codeages.biz.security.entity.User;
import com.codeages.biz.security.entity.UserStatus;
import com.codeages.core.http.ResponseWrapper;

public class UserControllerTest extends BaseControllerTest {
	
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
		
		HttpHeaders headers = makeHttpHeaders();
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);

		ResponseWrapper<User> savedUser = restTemplate.postForObject("/api/user", requestEntity, ResponseWrapper.class);
		if (savedUser.getBody()  instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) savedUser.getBody();
			Assert.assertEquals(user.getEmail(), map.get("email").toString());
		} else {
			throw new RuntimeException();
		}
	}

	@Test
	public void testGetUser() {
		HttpHeaders headers = makeHttpHeaders();
		Map addUser = addUser("test001");
		HttpEntity<HashMap> requestEntity = new HttpEntity<HashMap>(new HashMap(), headers);

		ResponseEntity<ResponseWrapper<Map>> response = restTemplate.exchange("/api/user/"+addUser.get("id"), HttpMethod.GET,
				requestEntity, ResponseWrapper.class, new HashMap());
		ResponseWrapper<Map> wrapperUser = response.getBody();
		Map user = wrapperUser.getBody();
		Assert.assertNotNull(user);
	}
	
	private Map addUser(String username)
	{
		User user = new User();
		user.setEmail(username+"@test.com");
		user.setPassword("test");
		user.setNickname(username);
		user.setName(username);
		user.setUsername(username);
		user.setPhone("13212345678");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);
		
		HttpHeaders headers = makeHttpHeaders();
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, headers);

		ResponseWrapper<User> savedUser = restTemplate.postForObject("/api/user", requestEntity, ResponseWrapper.class);
		if (savedUser.getBody()  instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) savedUser.getBody();
			Assert.assertEquals(user.getEmail(), map.get("email").toString());
			return map;
		} else {
			throw new RuntimeException();
		} 
	}
}
