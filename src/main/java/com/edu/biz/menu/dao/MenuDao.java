package com.edu.biz.menu.dao;

import java.util.List;

import com.edu.biz.base.BaseDao;
import com.edu.biz.menu.entity.Menu;

public interface MenuDao extends BaseDao<Menu> {

	Menu findByCode(String code);
	
//	@Query(value = "SELECT * FROM menu WHERE parent_id = ?", nativeQuery = true)
	List<Menu> findByParentId(Long id);

}
