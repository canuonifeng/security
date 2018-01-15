package com.codeages.security.biz.setting.dao;

import com.codeages.framework.base.BaseDao;
import com.codeages.security.biz.setting.entity.Setting;

public interface SettingDao extends BaseDao<Setting> {

	public Boolean deleteByCode(String code);

	public Setting getByCode(String code);

}
