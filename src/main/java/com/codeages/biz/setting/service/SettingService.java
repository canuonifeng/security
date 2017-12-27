package com.codeages.biz.setting.service;

import com.codeages.biz.setting.entity.Setting;

public interface SettingService {
	
	public Setting modifySetting(Setting setting);

	public Setting getSettingByCode(String code);
}
