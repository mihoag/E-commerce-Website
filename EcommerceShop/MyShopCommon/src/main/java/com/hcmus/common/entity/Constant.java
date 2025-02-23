package com.hcmus.common.entity;

public class Constant {
	public static final String S3_BASE_URI;
	public static final String ADMIN_SOCKET_CONNECTION_URI;
	public static final String CLIENT_SOCKET_CONNECTION_URI;
	public static final String RECAPTCHA_VERIFY_URL;
	public static final String CAPTCHA_SECRET_KEY;
	public static final String AI_SERVER_URI;

	static {
		String bucketName = System.getenv("AWS_BUCKET_NAME");
		String region = System.getenv("AWS_REGION");
		String pattern = "https://%s.s3.%s.amazonaws.com";
		S3_BASE_URI = bucketName == null ? "" : String.format(pattern, bucketName, region);

		ADMIN_SOCKET_CONNECTION_URI = String.format("wss://%s/MyshopAdmin/websocket", System.getenv("ADMIN_DOMAIN_URI"));
		CLIENT_SOCKET_CONNECTION_URI = String.format("wss://%s/Myshop/websocket", System.getenv("CLIENT_DOMAIN_URI"));
		
		RECAPTCHA_VERIFY_URL = System.getenv("RECAPTCHA_VERIFY_URL");
		CAPTCHA_SECRET_KEY = System.getenv("CAPTCHA_SECRET_KEY");
		AI_SERVER_URI = System.getenv("AI_SERVER_URI");		
	}
}
