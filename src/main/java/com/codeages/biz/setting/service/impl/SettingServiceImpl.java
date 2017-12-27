package com.codeages.biz.setting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeages.biz.base.BaseService;
import com.codeages.biz.setting.dao.SettingDao;
import com.codeages.biz.setting.entity.Setting;
import com.codeages.biz.setting.service.SettingService;
import com.codeages.core.util.BeanUtils;

@Service
public class SettingServiceImpl extends BaseService implements SettingService {

	@Autowired
	private SettingDao settingDao;
	
	@Override
	public Setting modifySetting(Setting setting) {
		Setting savedSetting = settingDao.getByCode(setting.getCode());
		if (null == savedSetting) {
			return settingDao.save(setting);
		}
		BeanUtils.copyPropertiesWithCopyProperties(setting, savedSetting, "value");
		return settingDao.save(savedSetting);
	}

	@Override
	public Setting getSettingByCode(String code) {
		return settingDao.getByCode(code);
	}
}
