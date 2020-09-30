package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingConfirmation  {
	private String booking_id;
	private String _cost;
	private ErrorDetails _errorDetails;

	public BookingConfirmation(String _errors) {	
		this.setErrorDetails(new ErrorDetails(_errors));
	}

	@JsonCreator
	public BookingConfirmation(@JsonProperty("booking_id") String _booking_id, @JsonProperty("cost") String _cost) {

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

	public ErrorDetails getErrorDetails() {
		return _errorDetails;
	}

	public void setErrorDetails(ErrorDetails _errorDetails) {
		this._errorDetails = _errorDetails;
	}

}
