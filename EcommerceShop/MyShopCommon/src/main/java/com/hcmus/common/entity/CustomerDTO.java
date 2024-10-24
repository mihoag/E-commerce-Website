package com.hcmus.common.entity;

public class CustomerDTO {

	private Integer id;
	private String firstName;
	private String lastName;

	public CustomerDTO() {
		super();
	}

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 */
	public CustomerDTO(Integer id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
