package com.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.common.dao.service.SettingService;
import com.edu.biz.common.entity.Setting;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/system")
@Api("系统设置")
public class SettingController extends BaseController<System> {
	@Autowired
	private SettingService settingService;

	@RequestMapping(method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('setting', 'edit')")
	public Setting modify(@RequestBody Setting setting) {
		return settingService.modifySetting(setting);
	}

	@RequestMapping(path = "/{code}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('setting', 'get')")
	public Setting get(@PathVariable String code) {
		return settingService.getSettingByCode(code);
	}
}
