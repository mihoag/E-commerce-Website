package com.hcmus.site.security.oauth;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.antlr.v4.runtime.misc.TestRig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hcmus.common.entity.AuthenticationType;
import com.hcmus.common.entity.Customer;
import com.hcmus.site.customer.CustomerService;
import com.hcmus.site.util.AmazonS3Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private CustomerService customerService;
	
	private final String CUSTOMER_IMAGE_DIR = "customers";

	private final Logger LOGGER = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

		CustomerOAuth2User oauth2User = (CustomerOAuth2User) authentication.getPrincipal();

		String name = oauth2User.getName();
		String email = oauth2User.getEmail();
		String countryCode = request.getLocale().getCountry();
		String clientName = oauth2User.getClientName();
		String imageUrl = oauth2User.getImageUrl();

		System.out.println(imageUrl);
		
		AuthenticationType authenticationType = getAuthenticationType(clientName);

		Customer customer = customerService.getCustomerByEmail(email);
		if (customer == null) {
			String image = uploadToS3(imageUrl);
			customerService.addNewCustomerUponOAuthLogin(name, email, countryCode, image, authenticationType);
		} else {
			
			if(customer.getImageUrl() == null || customer.getImageUrl().equals(""))
			{
				
				String image = uploadToS3(imageUrl);
				customer.setImageUrl(image);
			}
			
			oauth2User.setFullName(customer.getFullName());
			
			customerService.updateAuthenticationType(customer, authenticationType);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	private String uploadToS3(String imageUrl)
	{
		String image = null;
		try {
			InputStream imageStream = downloadImage(imageUrl);
			
			image = "image_" + UUID.randomUUID().toString() + ".png";
		    AmazonS3Util.uploadFile(CUSTOMER_IMAGE_DIR, image, imageStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage());
		}
		
		return image;
	}

	private AuthenticationType getAuthenticationType(String clientName) {
		if (clientName.equals("Google")) {
			return AuthenticationType.GOOGLE;
		} else if (clientName.equals("Facebook")) {
			return AuthenticationType.FACEBOOK;
		} else {
			return AuthenticationType.DATABASE;
		}
	}
	
	private void downloadAndSaveImage(String imageUrl, String destinationFile) throws Exception {
        InputStream imageStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            imageStream = downloadImage(imageUrl);
            fileOutputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[4096];
            int bytesRead;

            // Read from InputStream and write to FileOutputStream
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("Download completed.");

        } finally {
            // Ensure streams are closed
            if (imageStream != null) {
                imageStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }


	private InputStream downloadImage(String imageUrl) throws Exception {
		URL url = new URL(imageUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);

		if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed to download image: " + connection.getResponseMessage());
		}

		return connection.getInputStream();
	}
	
	public static void main(String[] args) throws Exception {
		OAuth2LoginSuccessHandler o =  new OAuth2LoginSuccessHandler();
		o.downloadAndSaveImage("https://lh3.googleusercontent.com/a/ACg8ocKwJ-YNd9ikdC4EWU6dXlsYnudmNxpzY1YpW6_E2xv9uTbHzoM8=s96-c", "downloaded_image.png");
	}
}
