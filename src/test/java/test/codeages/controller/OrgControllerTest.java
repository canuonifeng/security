package test.codeages.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.codeages.biz.org.entity.Organization;
import com.codeages.core.http.ResponseWrapper;

public class OrgControllerTest  extends BaseControllerTest{

	@Test
	public void testCreateOrg() {
		Organization org = this.getOrg();
		// 拿到header信息
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-auth-token", authToken);
		HttpEntity requestEntity = new HttpEntity(org, headers);

		ResponseWrapper savedOrg = restTemplate.postForObject("/api/org", requestEntity, ResponseWrapper.class);
		if (savedOrg.getBody()  instanceof Map) {
			Map map = (Map) savedOrg.getBody();
			Assert.assertEquals(org.getCode(), map.get("code").toString());
			Assert.assertEquals(org.getName(), map.get("name").toString());
			Assert.assertNotNull(map.get("children").toString());
			Assert.assertNotNull(map.get("faculty").toString());
		} else {
			throw new RuntimeException();
		}
	}
	
	@Test
	public void testGetOrg( ) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-auth-token", authToken);
		HttpEntity requestEntity = new HttpEntity(new HashMap(), headers);

		ResponseEntity<ResponseWrapper<Map>> response = restTemplate.exchange("/api/org/1", HttpMethod.GET,
				requestEntity, ResponseWrapper.class, new HashMap());
		ResponseWrapper<Map> wrapperOrg = response.getBody();
		Map org = wrapperOrg.getBody();
		Assert.assertNotNull(org);
	}
	
	
	private Organization getOrg() {
		Organization org = new Organization();		
		Organization child = new Organization();
		child.setCode("ER_ZI");
		child.setName("咸鱼的儿子");
		
		List<Organization> children =  new ArrayList<>();
		children.add(child);
		org.setCode("XIAN_YU");
		org.setChildren(children);
		org.setName("会喊666的咸鱼");
		
		return org;
	}
}
