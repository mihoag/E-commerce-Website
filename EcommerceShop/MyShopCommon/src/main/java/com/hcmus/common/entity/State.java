package com.hcmus.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "states")
public class State extends IdBasedEntity {

	@Column(nullable = false, length = 45)
	private String name;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	public State(String name, Country country) {
		super();
		this.name = name;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "State [name=" + name + "]";
	}

}
