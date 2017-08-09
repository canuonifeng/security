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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.schoolroll.entity.Major;
import com.edu.biz.schoolroll.entity.MajorStatus;
import com.edu.biz.schoolroll.service.MajorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/major")
@Api("专业")
public class MajorController extends BaseController<Major> {
	@Autowired
	private MajorService majorService;
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('major', 'add')")
	public Major add(@RequestBody Major major) {
		return majorService.createMajor(major);
	}
	
	@RequestMapping(path = "/check_code",method = RequestMethod. GET)
	@ApiOperation(value = "检查专业代码是否重复", notes = "根据专业代码检查是否重复")
	public Boolean checkCode(String code,  Long majorId){
		 return majorService.checkCode(code, majorId);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('major', 'edit')")
	public Major edit(@PathVariable Long id, @RequestBody Major major) {
		major.setId(id);
		return majorService.updateMajor(major);
	}
	
	@RequestMapping(path = "/{id}/status", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('major', 'edit')")
	@ApiOperation(value = "修改院系状态")
	@ApiResponses({ @ApiResponse(code = 401, message = "没有登录"), @ApiResponse(code = 403, message = "没有权限"), })
	public Major changeMajorStatus(@PathVariable @ApiParam(name = "id", value = "专业ID", required = true) Long id,
			@RequestBody @ApiParam(name = "status", value = "enable(启用)，disable(禁用)", required = true) Map<String, String> params) {
		MajorStatus status = MajorStatus.valueOf(params.get("status"));
		return majorService.changeMajorStatus(id, status);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('major', 'delete')")
	public boolean delete(@PathVariable Long id) {
		return majorService.deleteMajor(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('major', 'get')")
	public Major get(@PathVariable Long id) {
		Major major = new Major();
		major.setId(id);
		return majorService.getMajor(major.getId());
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('major', 'get')")
	public Page<Major> pager(@RequestParam Map<String, Object> conditions,
			@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		return majorService.searchMajor(conditions, pageable);
	}
}