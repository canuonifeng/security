package com.edu.biz.menu.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.edu.biz.menu.dao.MenuDao;
import com.edu.biz.menu.entity.Menu;
import com.edu.biz.menu.service.MenuService;
import com.edu.biz.security.service.RoleService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private RoleService roleService;

	@Override
	public List<Menu> findChildMenus(String code) {
		Menu menu = menuDao.findByCode(code);
		List<Menu> menus = menuDao.findByParentId(menu.getId());

		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Set<String> roleCodes = new HashSet<String>();
		for (GrantedAuthority grantedAuthority : authorities) {
			roleCodes.add(grantedAuthority.getAuthority());
		}
		Set<String> permissions = roleService.findByPermissionCodes(roleCodes);

		List<Menu> menuArray = new ArrayList<Menu>();
		for (Menu item : menus) {
			if (permissions.contains(item.getPermission().getCode())) {
				menuArray.add(item);
			}
		}

		return menuArray;
	}
}
