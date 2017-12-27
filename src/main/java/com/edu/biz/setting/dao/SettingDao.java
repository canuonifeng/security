package com.edu.biz.setting.dao;

import com.edu.biz.base.BaseDao;
import com.edu.biz.setting.entity.Setting;

public interface SettingDao extends BaseDao<Setting> {

	public Boolean deleteByCode(String code);

	public Setting getByCode(String code);

}
