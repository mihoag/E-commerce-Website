package com.hcmus.admin.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.setting.Setting;
import com.hcmus.common.entity.setting.SettingCategory;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SettingService {
    @Autowired private SettingRepository repo;
  
	public List<Setting> listAllSettings() {
		return (List<Setting>) repo.findAll();
	}
	
	public GeneralSettingBag getGeneralSettings() {
		List<Setting> settings = new ArrayList<>();
		
		List<Setting> generalSettings = repo.findByCategory(SettingCategory.GENERAL);
		List<Setting> currencySettings = repo.findByCategory(SettingCategory.CURRENCY);
		
		settings.addAll(generalSettings);
		settings.addAll(currencySettings);
		
		return new GeneralSettingBag(settings);
	}
	
	public MailSettingBag getMailSettings()
	{
		List<Setting> settings = new ArrayList<>();
		List<Setting> mailServerSettings = repo.findByCategory(SettingCategory.MAIL_SERVER);
		List<Setting> mailTemplateSettings = repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
		
		settings.addAll(mailServerSettings);
		settings.addAll(mailTemplateSettings);
		
		return new MailSettingBag(settings);
		
	}
	
	public void saveAll(Iterable<Setting> settings) {
		repo.saveAll(settings);
	}
	
	public void updateSetting(String key, String value)
	{
		List<Setting> listSettings = repo.findByKey(key);
		if(listSettings != null)
		{
			Setting s = listSettings.get(0);
			System.out.println(s);
			s.setValue(value);
			repo.save(s);
		}
	}
	
	public List<Setting> getMailServerSettings() {
		return repo.findByCategory(SettingCategory.MAIL_SERVER);
	}
	
	public List<Setting> getMailTemplateSettings() {
		return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
	}	
	
	public List<Setting> getCurrencySettings() {
		return repo.findByCategory(SettingCategory.CURRENCY);
	}
	
	public List<Setting> getPaymentSettings() {
		return repo.findByCategory(SettingCategory.PAYMENT);
	}	
}
