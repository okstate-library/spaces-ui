package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.okstatelibrary.spacesui.util.DateTimeUtil;

public class BookedItem {
	private String bookId;
	private String eid;
	private String cid;
	private String lid;
	private String date;
	private String fromDate;
	private String toDate;
	private String firstName;
	private String lastName;
	private String email;
	private String status;
	private String room;


	@JsonCreator
	public BookedItem(@JsonProperty("bookId") String _bookId, @JsonProperty("eid") String _eid,
			@JsonProperty("cid") String _cid, @JsonProperty("lid") String _lid,
			@JsonProperty("fromDate") String _fromDate, @JsonProperty("toDate") String _toDate,
			@JsonProperty("firstName") String _firstName, @JsonProperty("lastName") String _lastName,
			@JsonProperty("email") String _email, @JsonProperty("status") String _status) {

		this.setBookId(_bookId);
		this.setEid(_eid);
		this.setCid(_cid);
		this.setLid(_lid);
		this.setFromDate(_fromDate);
		this.setToDate(_toDate);
		this.setFirstName(_firstName);
		this.setLastName(_lastName);
		this.setEmail(_email);
		this.setStatus(_status);
		
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getFromDate() {
		return DateTimeUtil.convertToTime(fromDate);
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return DateTimeUtil.convertToTime(toDate);
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getDate() {
		return DateTimeUtil.convertToDate(fromDate);
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
