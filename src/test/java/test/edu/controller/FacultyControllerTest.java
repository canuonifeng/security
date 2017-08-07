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

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.service.FacultyService;
import com.edu.core.ResponseWrapper;

public class FacultyControllerTest extends BaseControllerTest {
	
	@Autowired
	private FacultyService facultyService;
	
	@Test
	public void testAddFaculty() {
		Faculty faculty = new Faculty();
		faculty.setName("test");
		faculty.setCode("test");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-auth-token", authToken);
		HttpEntity requestEntity = new HttpEntity(faculty, headers);
		
		ResponseWrapper savedFaculty = restTemplate.postForObject("/api/faculty", requestEntity, ResponseWrapper.class);
		if (savedFaculty.getBody()  instanceof Map) {
			Map map = (Map) savedFaculty.getBody();
			Assert.assertEquals(faculty.getCode(), map.get("code").toString());
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

		ResponseEntity<ResponseWrapper<Map>> response = restTemplate.exchange("/api/faculty/1", HttpMethod.GET,
				requestEntity, ResponseWrapper.class, new HashMap());
		ResponseWrapper<Map> wrapperFaculty = response.getBody();
		Map faculty = wrapperFaculty.getBody();
		Assert.assertNotNull(faculty);
	}
}
