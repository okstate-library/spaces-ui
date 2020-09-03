package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SAMLUser {

	private String userId;
	private String firstName;
	private String lastName;
	private String cwid;
	private String email;

	@JsonCreator
	public SAMLUser(@JsonProperty("user_Id") String userId, @JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName, @JsonProperty("cwid") String cwid,
			@JsonProperty("email") String email) {
		this.setUser(userId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setCwid(cwid);
		this.setEmail(email);
	}

	public String getUser() {
		return userId;
	}

	public void setUser(String userId) {
		this.userId = userId;
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

	public String getCwid() {
		return cwid;
	}

	public void setCwid(String cwid) {
		this.cwid = cwid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
