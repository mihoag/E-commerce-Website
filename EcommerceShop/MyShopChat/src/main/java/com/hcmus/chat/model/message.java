package com.hcmus.chat.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class message {

	@Id
	private ObjectId id;

	private String content;

	private Integer customerId;
	private String customerName;
	private String customerImage;

	private Integer userId;
	private String userImage;

	private String time; // Store as String for simplicity (could also use LocalDateTime)

	private RoleChat role_chat;

	/**
	 * 
	 */
	public message() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param content
	 * @param customerId
	 * @param customerName
	 * @param customerImage
	 * @param userId
	 * @param userImage
	 * @param time
	 * @param roleChat
	 */
	public message(String content, Integer customerId, String customerName, String customerImage,
			Integer userId, String userImage, String time, RoleChat roleChat) {
		super();
		this.content = content;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerImage = customerImage;
		this.userId = userId;
		this.userImage = userImage;
		this.time = time;
		this.role_chat = roleChat;
	}

	public ObjectId getId() {
		return id;
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerImage() {
		return customerImage;
	}

	public void setCustomerImage(String customerImage) {
		this.customerImage = customerImage;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public RoleChat getRole_chat() {
		return role_chat;
	}

	public void setRole_chat(RoleChat role_chat) {
		this.role_chat = role_chat;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "message [id=" + id + ", content=" + content + ", customerId=" + customerId + ", customerName="
				+ customerName + ", customerImage=" + customerImage + ", userId=" + userId + ", userImage=" + userImage
				+ ", time=" + time + ", roleChat=" + role_chat + "]";
	}

}
