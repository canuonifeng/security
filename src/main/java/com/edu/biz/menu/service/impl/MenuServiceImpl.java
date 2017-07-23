package com.edu.biz.menu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.menu.dao.MenuDao;
import com.edu.biz.menu.entity.Menu;
import com.edu.biz.menu.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> findChildMenus(String code) {
		Menu menu = menuDao.findByCode(code);
		return menuDao.findByParentId(menu.getId());
	}
}
