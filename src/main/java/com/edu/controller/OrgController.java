package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.Organization;
import com.edu.biz.security.service.OrgService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/org")
@Api("组织机构")
public class OrgController extends BaseController<Organization> {
	@Autowired
	private OrgService orgService;

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('org', 'add')")
	public Organization add(@RequestBody Organization org) {
		return orgService.createOrg(org);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('org', 'edit')")
	public Organization edit(@PathVariable Long id, @RequestBody Organization org) {
		return orgService.updateOrg(org);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('org', 'delete')")
	public boolean delete(@PathVariable Long id, @RequestBody Organization org) {
		return orgService.deleteOrg(org.getId());
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('org', 'get')")
	public Organization get(@PathVariable Long id) {
		Organization org = new Organization();
		org.setId(id);
		return orgService.getOrg(org.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('org', 'get')")
	public Page<Organization> pager(Map<String, Object> conditions,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return orgService.searchOrgs(conditions, pageable);
	}
}
