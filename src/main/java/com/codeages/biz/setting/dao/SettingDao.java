package com.codeages.biz.setting.dao;

import com.codeages.biz.setting.entity.Setting;
import com.codeages.framework.base.BaseDao;

public interface SettingDao extends BaseDao<Setting> {

	public Boolean deleteByCode(String code);

	public Setting getByCode(String code);

}
