package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.okstatelibrary.spacesui.util.DateTimeUtil;

public class Availability {

	private String from;
	private String to;

	private String fromDateTime;
	private String toDateTime;

	private String fromTime;
	private String toTime;

	private boolean visible;
	private boolean booked;

	@JsonCreator
	public Availability(@JsonProperty("from") String _from, @JsonProperty("to") String to) {
		this.setFrom(_from);
		this.setTo(to);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	

	public String getFromTime() {
		return DateTimeUtil.convertToTime(this.from);
	}

	public String getToTime() {
		return DateTimeUtil.convertToTime(this.to);
	}

	public String getFromDateTime() {
		return this.from; //convertToDateTime(this.from);
	}

	public String getToDateTime() {
		return DateTimeUtil.convertToDateTime(this.to);

	}

//	public String getromTime() {
//		return convertToTime(this.from);
//	}
//
//	public String getStrTo() {
//		return  convertToTime(this.to);
//	}

}
