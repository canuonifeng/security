package com.edu.biz.common.dao.service;

import com.edu.biz.common.entity.Setting;

public interface SettingService {
	
	public Setting modifySetting(Setting setting);

	public Setting getSettingByCode(String code);
}
