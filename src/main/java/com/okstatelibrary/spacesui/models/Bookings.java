package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bookings {
	private String id;
	private String to;
	
	@JsonCreator
	public Bookings(@JsonProperty("id") String _id, @JsonProperty("to") String _to) {

		this.setId(_id);
		this.setTo(_to);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

}
