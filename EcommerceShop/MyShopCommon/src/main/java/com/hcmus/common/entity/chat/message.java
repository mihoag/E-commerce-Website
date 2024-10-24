package com.hcmus.common.entity.chat;

import java.util.Date;

import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.IdBasedEntity;
import com.hcmus.common.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message")
public class message extends IdBasedEntity {

	@Column
	private String content;

	@Enumerated(EnumType.STRING)
	private RoleChat role_chat;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column
	private Date time;

	public message() {
		super();
	}

	/**
	 * @param content
	 * @param role_chat
	 * @param customer
	 * @param user
	 * @param time
	 */
	public message(String content, RoleChat role_chat, Customer customer, User user, Date time) {
		super();
		this.content = content;
		this.role_chat = role_chat;
		this.customer = customer;
		this.user = user;
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RoleChat getRole_chat() {
		return role_chat;
	}

	public void setRole_chat(RoleChat role_chat) {
		this.role_chat = role_chat;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "message [content=" + content + ", role_chat=" + role_chat + ", customer=" + customer + ", user=" + user
				+ ", time=" + time + "]";
	}

}
