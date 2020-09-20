package com.okstatelibrary.spacesui.models;

public class BookingUI {
	private String date;
	private String startTime;
	private String endTime;
	private String roomName;
	private String roomNumber;

	public BookingUI(String date, String startTime, String endTime, String roomName, String roomNumber) {

		this.setDate(date);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setRoomName(roomName);
		this.setRoomNumber(roomNumber);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

}
