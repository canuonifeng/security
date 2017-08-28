package com.edu.biz.common.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.common.entity.Setting;

public interface SettingDao extends BaseDao<Setting> {

	public Boolean deleteByCode(String code);

	public Setting getByCode(String code);

}
