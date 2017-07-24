package com.edu.biz.menu.service;

import java.util.List;

import com.edu.biz.menu.entity.Menu;

public interface MenuService {

	List<Menu> findChildMenus(String code);
}
