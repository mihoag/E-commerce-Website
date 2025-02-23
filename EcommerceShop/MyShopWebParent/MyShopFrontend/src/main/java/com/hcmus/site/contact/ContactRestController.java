package com.hcmus.site.contact;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcmus.site.Utility;
import com.hcmus.site.setting.MailSettingBag;
import com.hcmus.site.setting.SettingService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/contact/sendmail")
public class ContactRestController {

	private static Logger LOGGER = LoggerFactory.getLogger(ContactRestController.class);
	private static final String FAIL_STATUS = "Fail";
	private static final String SUCCESS_STATUS = "OK";

	@Autowired
	private SettingService settingService;

	@PostMapping
	public String contactPost(String fullname, String email, String phone, String subject, String message) {
		try {
			sendEmail(fullname, email, phone, subject, message);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return FAIL_STATUS;
		}
		return SUCCESS_STATUS;
	}

	private void sendEmail(String fullname, String email, String phone, String subject, String mess)
			throws UnsupportedEncodingException, MessagingException {
		MailSettingBag emailSettings = settingService.getMailSettings();
		JavaMailSenderImpl mailSender = Utility.prepareMailSender(emailSettings);

		String toAddress = emailSettings.getUsername();
		String content = String.format(
				"<p>Fullname:  %s </p><p>Email: %s </p><p>Phone:  %s</p><p>Subject:  %s </p><br><p>Message: %s </p>",
				fullname, email, phone, subject, mess);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
		helper.setTo(toAddress);
		helper.setSubject(subject);

		helper.setText(content, true);
		mailSender.send(message);
	}
}
