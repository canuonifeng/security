package com.edu.biz.setting.service;

import com.edu.biz.setting.entity.Setting;

public interface SettingService {
	
	public Setting modifySetting(Setting setting);

	public Setting getSettingByCode(String code);
}
