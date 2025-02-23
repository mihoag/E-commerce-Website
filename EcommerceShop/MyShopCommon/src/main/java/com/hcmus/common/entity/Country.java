package com.hcmus.common.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "countries")
public class Country extends IdBasedEntity {
	@Column(nullable = false, length = 45)
	private String name;

	@Column(nullable = false, length = 5)
	private String code;

	@OneToMany(mappedBy = "country")
	private Set<State> states;

	public Country() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Country(Integer id) {
		setId(id);
	}

	public Country(String name, String code, Set<State> states) {
		super();
		this.name = name;
		this.code = code;
		this.states = states;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	@Override
	public String toString() {
		return "Country [name=" + name + ", code=" + code + ", states=" + states + "]";
	}
}
