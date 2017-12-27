package com.codeages.biz.setting.dao;

import com.codeages.biz.base.BaseDao;
import com.codeages.biz.setting.entity.Setting;

public interface SettingDao extends BaseDao<Setting> {

	public Boolean deleteByCode(String code);

	public Setting getByCode(String code);

}
