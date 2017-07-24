package com.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.biz.menu.entity.Menu;
import com.edu.biz.menu.service.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController<Menu>{
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(path = "/{code}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public List<Menu> findChildMenus(@PathVariable String code) {
		return menuService.findChildMenus(code);
	}
}
