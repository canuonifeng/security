package com.codeages.security.biz.setting.service;

import com.codeages.security.biz.setting.entity.Setting;

public interface SettingService {
	
	public Setting modifySetting(Setting setting);

	public Setting getSettingByCode(String code);
}
