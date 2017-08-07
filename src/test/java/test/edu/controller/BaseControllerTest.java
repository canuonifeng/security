package test.edu.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.edu.Application;
import com.edu.biz.security.entity.Gender;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.core.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class BaseControllerTest {
	@Autowired
	protected TestRestTemplate restTemplate;

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	protected String authToken;

	@Before
	public void before() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "admin");
		map.put("password", "test1");
		ResponseEntity<ResponseWrapper> response = restTemplate.postForEntity("/login", map, ResponseWrapper.class);
		ResponseWrapper wrapperedResponse = response.getBody();
		Map responseMap = (Map)wrapperedResponse.getBody();
		authToken = responseMap.get("token").toString();
	}

	@Test
	public void test() throws Exception {
		User user = new User();
		user.setEmail("test110@test1.com");
		user.setPassword("test110");
		user.setNickname("test110");
		user.setName("test110");
		user.setUsername("test110");
		user.setPhone("1999999999");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);

		ObjectMapper mapper = new ObjectMapper();
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/user")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user))
				.header("x-auth-token", authToken)
				.accept(MediaType.APPLICATION_JSON_UTF8);
		
		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Honda Civic"));
	}

}