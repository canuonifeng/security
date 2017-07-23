package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.Organization;
import com.edu.biz.security.service.OrgService;

@RestController
@RequestMapping("/api/org")
public class OrgController extends BaseController<Organization>{
	@Autowired
	private OrgService orgService;
	
	@RequestMapping(method = RequestMethod.POST)
	public Organization add(@RequestBody Organization org) {
		return orgService.createOrg(org);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public Organization edit(@PathVariable Long id, @RequestBody Organization org) {
		return orgService.updateOrg(org);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable Long id, @RequestBody Organization org) {
		return orgService.deleteOrg(org.getId());
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Organization get(@PathVariable Long id) {
		Organization org = new Organization();
		org.setId(id);
		return orgService.getOrg(org.getId());
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<Organization> pager(Map<String, String> conditions, @PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return orgService.searchOrgs(conditions, pageable);
	}
}
