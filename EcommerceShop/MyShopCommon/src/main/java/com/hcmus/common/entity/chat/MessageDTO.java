package com.hcmus.common.entity.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {
	private int id;
	private String content;

	@JsonProperty("customerId")
	private Integer customerId;

	@JsonProperty("customerName")
	private String customerName;

	@JsonProperty("userId")
	private Integer userId;

	private String time;

	private RoleChat role_chat;

	public MessageDTO() {
		super();
	}

	/**
	 * @param id
	 * @param content
	 * @param customerId
	 * @param customerName
	 * @param userId
	 * @param time
	 * @param role_chat
	 */
	public MessageDTO(int id, String content, Integer customerId, String customerName, Integer userId, String time,
			RoleChat role_chat) {
		super();
		this.id = id;
		this.content = content;
		this.customerId = customerId;
		this.customerName = customerName;
		this.userId = userId;
		this.time = time;
		this.role_chat = role_chat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Override
	public String toString() {
		return "MessageDTO [id=" + id + ", content=" + content + ", customerId=" + customerId + ", userId=" + userId
				+ ", time=" + time + ", role_chat=" + role_chat + "]";
	}

}
