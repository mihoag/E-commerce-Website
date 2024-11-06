package com.hcmus.common.entity;

public class CustomerDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String imageUrl;
	private int unseenMessageCount;
	
	public CustomerDTO() {
		super();
	}

	

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param imageUrl
	 */
	



	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param imageUrl
	 * @param unseenMessageCount
	 */
	public CustomerDTO(Integer id, String firstName, String lastName, String imageUrl, int unseenMessageCount) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.imageUrl = imageUrl;
		this.unseenMessageCount = unseenMessageCount;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public int getUnseenMessageCount() {
		return unseenMessageCount;
	}

	public void setUnseenMessageCount(int unseenMessageCount) {
		this.unseenMessageCount = unseenMessageCount;
	}
	
}
