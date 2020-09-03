package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelConfirmation {
	private String booking_id;
	private String cancelled;

	@JsonCreator
	public CancelConfirmation(@JsonProperty("booking_id") String _booking_id, @JsonProperty("cancelled") String cancelled) {

		this.setBooking_id(_booking_id);
		this.setCancelled(cancelled);
	}

	public String getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(String booking_id) {
		this.booking_id = booking_id;
	}

	public String getCancelled() {
		return cancelled;
	}

	public void setCancelled(String _cost) {
		this.cancelled = _cost;
	}

}
