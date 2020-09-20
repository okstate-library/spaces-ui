package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingConfirmation extends ErrorDetails {
	private String booking_id;
	private String _cost;

	@JsonCreator
	public BookingConfirmation(@JsonProperty("errors") String _errors) {
		super(_errors);
	}

	@JsonCreator
	public BookingConfirmation(@JsonProperty("booking_id") String _booking_id, @JsonProperty("cost") String _cost,
			@JsonProperty("errors") String _errors) {

		super(_errors);

		this.setBooking_id(_booking_id);
		this.set_cost(_cost);
	}

	public String getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(String booking_id) {
		this.booking_id = booking_id;
	}

	public String get_cost() {
		return _cost;
	}

	public void set_cost(String _cost) {
		this._cost = _cost;
	}

}
