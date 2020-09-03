package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomBookingPayload {
	private String start;
	private String fname;
	private String lname; 
	private String email;
	private String q4200;
	private Bookings[] bookings;
	
	@JsonCreator
	public RoomBookingPayload(@JsonProperty("start") String _start, @JsonProperty("fname") String _fname,
			@JsonProperty("lname") String _lname, @JsonProperty("email") String _email,
			@JsonProperty("q4200") String _q4200, @JsonProperty("bookings") Bookings[] _bookings) {

		//,JsonProperty("test") boolean _test
		this.setStart(_start);
		this.setFname(_fname);
		this.setLname(_lname);
		this.setEmail(_email);
		this.setQ4200(_q4200);
		this.setBookings(_bookings);
		//this.setTest(_test);		
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getQ4200() {
		return q4200;
	}
	public void setQ4200(String q4200) {
		this.q4200 = q4200;
	}
	
	public Bookings[] getBookings() {
		return bookings;
	}
	public void setBookings(Bookings[] bookings) {
		this.bookings = bookings;
	}	
}
