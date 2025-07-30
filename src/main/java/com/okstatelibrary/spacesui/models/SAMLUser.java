package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.okstatelibrary.spacesui.util.DateTimeUtil;

public class SAMLUser {

	private String userId;
	private String firstName;
	private String lastName;
	private String cwid;
	private String email;
	private String currentTime;

	@JsonCreator
	public SAMLUser(@JsonProperty("user_Id") String userId, @JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName, @JsonProperty("cwid") String cwid,
			@JsonProperty("email") String email) {
		this.setUserId(userId);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setCwid(cwid);
		this.setEmail(email);
		this.setCurrentTime(DateTimeUtil.getTodayDateWithTime());
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public void print() {
		System.out.println(this.userId + "--" + this.cwid + "--" + this.firstName + "--" + this.email);

	}

	/**
	 * @return the currentTime
	 */
	public String getCurrentTime() {
		return currentTime;
	}

	/**
	 * @param currentTime the currentTime to set
	 */
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

}
