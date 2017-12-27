package test.codeages.controller;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.codeages.Application;
import com.codeages.biz.dict.Gender;
import com.codeages.biz.security.entity.Role;
import com.codeages.biz.security.entity.User;
import com.codeages.biz.security.entity.UserStatus;
import com.codeages.biz.security.service.UserService;
import com.codeages.core.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class BaseControllerTest {
	
	@SpyBean
	private UserService userService;
	@SpyBean
	private UserDetailsService userDetailsService;
	
	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected String authToken;
	
	private User makeAdminUser()
	{
		User user = new User();
		user.setId(1L);
		user.setEmail("admin@admin.com");
		user.setPassword("test1");
		user.setNickname("admin");
		user.setName("admin");
		user.setUsername("admin");
		user.setPhone("1999999999");
		user.setGender(Gender.female);
		user.setStatus(UserStatus.enable);
		user.setPassword("56e88fcd763ee7a36c409ef6cf852aea");
		user.setSalt("ujQAwILUUuVemyxz");
	    return user;
	}

	@Before
	public void before() throws Exception {
		User admin = makeAdminUser();
		given(userDetailsService.loadUserByUsername("admin")).willReturn(admin);
		given(userService.getUserById(1L)).willReturn(admin);
		given(userService.updateUser(admin)).willReturn(admin);
		given(userService.getUserById(1L)).willReturn(admin);
		
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setName("超级管理员");
		role.setCode("ROLE_SUPER_ADMIN");
		roles.add(role);
		admin.setRoles(roles);
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "admin");
		map.put("password", "test1");
		ResponseEntity<ResponseWrapper> response = restTemplate.postForEntity("/login", map, ResponseWrapper.class);
		ResponseWrapper wrapperedResponse = response.getBody();
		Map responseMap = (Map)wrapperedResponse.getBody();
		authToken = responseMap.get("token").toString();
	}
	
	protected HttpHeaders makeHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-auth-token", authToken);
		return headers;
	}

//	@Test
//	public void test() throws Exception {
//		User user = new User();
//		user.setEmail("test110@test1.com");
//		user.setPassword("test110");
//		user.setNickname("test110");
//		user.setName("test110");
//		user.setUsername("test110");
//		user.setPhone("1999999999");
//		user.setGender(Gender.female);
//		user.setStatus(UserStatus.enable);
//
//		ObjectMapper mapper = new ObjectMapper();
//		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/user")
//				.contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user))
//				.header("x-auth-token", authToken)
//				.accept(MediaType.APPLICATION_JSON_UTF8);
//		
//		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string("Honda Civic"));
//	}

}