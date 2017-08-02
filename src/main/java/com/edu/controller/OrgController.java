package com.edu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.security.entity.Organization;
import com.edu.biz.security.entity.validgroup.Update;
import com.edu.biz.security.service.OrgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	public Organization edit(@PathVariable Long id, @Validated( { Update.class }) @RequestBody Organization org) {
		org.setId(id);
		return orgService.updateOrg(org);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('org', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return orgService.deleteOrg(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('org', 'get')")
	public Organization get(@PathVariable Long id) {
		Organization org = new Organization();
		org.setId(id);
		return orgService.getOrg(org.getId());
	}
	
	@RequestMapping(path = "/check_code",method = RequestMethod. GET)
	@ApiOperation(value = "检查组织机构编号是否重复", notes = "根据组织机构编号检查是否重复")
	public Boolean checkCode(String code,  Long orgId){
		 return orgService.checkCode(code, orgId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('org', 'get')")
	public Page<Organization> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return orgService.searchOrgs(conditions, pageable);
	}
}
