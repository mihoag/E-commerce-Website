package com.hcmus.common.entity.setting;

import java.util.List;

public class SettingBag {
	
	private List<Setting> listSettings;

	public SettingBag(List<Setting> listSettings) {
		super();
		this.listSettings = listSettings;
	}
	
	public Setting get(String key)
	{
		Setting setting = new Setting(key);
		int index = listSettings.indexOf(setting);
		if(index >= 0)
		{
			return listSettings.get(index);
		}
		return null;
	}
	
	public String getValue(String key) {
		Setting setting = get(key);
		if (setting != null) {
			return setting.getValue();
		}
		
		return null;
	}
	
	public void update(String key, String value) {
		Setting setting = get(key);
		if (setting != null && value != null) {
			setting.setValue(value);
		}
	}
}
