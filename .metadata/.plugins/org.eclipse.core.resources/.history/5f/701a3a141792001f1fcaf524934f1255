package com.hcmus.site.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Currency;
import com.hcmus.common.entity.setting.Setting;
import com.hcmus.common.entity.setting.SettingCategory;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SettingService {
    @Autowired private SettingRepository repo;
  
    @Autowired private CurrencyRepository currencyRepo;
	public List<Setting> listAllSettings() {
		return (List<Setting>) repo.findAll();
	}
	
	public List<Setting> getGeneralSettings() {
		List<Setting> settings = new ArrayList<>();
		
		List<Setting> generalSettings = repo.findByCategory(SettingCategory.GENERAL);
		List<Setting> currencySettings = repo.findByCategory(SettingCategory.CURRENCY);
		
		settings.addAll(generalSettings);
		settings.addAll(currencySettings);
		
		return settings;
	}
	
	public void saveAll(Iterable<Setting> settings) {
		repo.saveAll(settings);
	}
	
	public void updateSetting(String key, String value)
	{
		
			Setting s = repo.findByKey(key);
			System.out.println(s);
			s.setValue(value);
			repo.save(s);
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
	
	public List<Setting> getMailServerSettings() {
		return repo.findByCategory(SettingCategory.MAIL_SERVER);
	}
	
	public List<Setting> getMailTemplateSettings() {
		return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
	}	
	

	public CurrencySettingBag getCurrencySettings()
	{
		List<Setting> currencySetting = repo.findByCategory(SettingCategory.CURRENCY);
	    return new CurrencySettingBag(currencySetting);
	}
	
	
	public List<Setting> getPaymentSettings() {
		return repo.findByCategory(SettingCategory.PAYMENT);
	}	
	
	public PaymentSettingBag getPaymentSettingBag()
	{
		return new PaymentSettingBag(getPaymentSettings());
	}
	
	public String getCurrencyCode() {
		Setting setting = repo.findByKey("CURRENCY_ID");
		Integer currencyId = Integer.parseInt(setting.getValue());
		Currency currency = currencyRepo.findById(currencyId).get();
		return currency.getCode();
	}
}
