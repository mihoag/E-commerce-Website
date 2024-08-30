package com.hcmus.admin.setting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.util.FileUploadUtil;
import com.hcmus.common.entity.Currency;
import com.hcmus.common.entity.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/setting")
public class SettingController {
    
	@Autowired private SettingService service;
	
	@Autowired private CurrencyRepository currencyRepo;
	
	@GetMapping("")
	public String listAll(Model model, HttpServletRequest request) {
		List<Setting> listSettings = service.listAllSettings();
		List<Currency> listCurrencies = currencyRepo.findAllByOrderByNameAsc();
		
		model.addAttribute("listCurrencies", listCurrencies);
		
		for (Setting setting : listSettings) {
			model.addAttribute(setting.getKey(), setting.getValue());
		}
		
	
		String tab = (String) request.getAttribute("tab");
		if(tab == null)
		{
			request.setAttribute("tab", "general");
		}
		
		model.addAttribute("sideBarFieldName", "setting");
		return "settings/settings";
	}
	
	@PostMapping("/save_general")
	public String saveGeneralSetting(@RequestParam("fileImage") MultipartFile multipartFile,
			HttpServletRequest request, RedirectAttributes ra) throws IllegalStateException, IOException {
		//TODO: process POST request
		GeneralSettingBag settingBag = service.getGeneralSettings();
		
		saveCurrencySymbol(request);
		saveSiteLogo(multipartFile);
		updateSettingValuesFromForm(request, settingBag.getListSettings());
		
		ra.addFlashAttribute("message", "General settings have been saved.");
		ra.addFlashAttribute("tab", "general");
		return "redirect:/setting";
	}
	
	@PostMapping("/save_mail_server")
	public String saveMailConfig(
			HttpServletRequest request, RedirectAttributes ra) throws IllegalStateException, IOException {
		//TODO: process POST request
	
		MailSettingBag mailSettingBag = service.getMailSettings();
		updateSettingValuesFromForm(request, mailSettingBag.getListSettings());
		
		ra.addFlashAttribute("message", "Mail settings have been saved.");
		ra.addFlashAttribute("tab", "mail");
		return "redirect:/setting";
	}
	
	@PostMapping("/save_mail_templates")
	public String saveMailTemplates(HttpServletRequest request, RedirectAttributes ra) {
		//TODO: process POST request
		
	    updateSettingValuesFromForm(request, service.getMailTemplateSettings());
		
		ra.addFlashAttribute("message", "Mail templates have been saved.");
		ra.addFlashAttribute("tab", "mailTemplate");
		return "redirect:/setting";
	}
	
	@PostMapping("/save_payment")
	public String savePayment(HttpServletRequest request, RedirectAttributes ra) {
	
	    updateSettingValuesFromForm(request, service.getPaymentSettings());
		
		ra.addFlashAttribute("message", "Payment settings have been saved.");
		ra.addFlashAttribute("tab", "payment");
		return "redirect:/setting";
	}
	
	
	public void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> settings)
	{
	    for(Setting setting : settings)
	    {
	    	String value = request.getParameter(setting.getKey());
			if (value != null) {
				service.updateSetting(setting.getKey(), value);
			}
	    }
	}
	
	public void  saveSiteLogo(MultipartFile multipartFile) throws IllegalStateException, IOException
	{
		if(!multipartFile.isEmpty())
		{
		
		String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		service.updateSetting("SITE_LOGO", filename);
		String UPLOAD_DIR = System.getProperty("user.dir") +  "/src/main/resources/static/img/";
		 
		FileUploadUtil.cleanDir(UPLOAD_DIR);
		//System.out.println(UPLOAD_DIR);
		 
		Path path = Paths.get(UPLOAD_DIR + filename);
		multipartFile.transferTo(new File(path.toString()));
		}
	}
	

	private void saveCurrencySymbol(HttpServletRequest request) {
		Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
		Optional<Currency> findByIdResult = currencyRepo.findById(currencyId);
		
		if (findByIdResult.isPresent()) {
			Currency currency = findByIdResult.get();
			//System.out.println(currency);
		    service.updateSetting("CURRENCY_ID", (currencyId)+"");
		}
	}
	
}
