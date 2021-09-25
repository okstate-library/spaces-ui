package com.okstatelibrary.spacesui.models;

public class ErrorDetails {

	private String errorId;

	public ErrorDetails(String errorString) {
		this.setErrorString(errorString);
	}

	public void setErrorString(String errors) {

		System.out.print("errors---" + errors);

		if (errors.contains("Sorry, this exceeds the limit per day in this category")) {
			this.setErrorId("303");
		} else if (errors.contains("is not a valid starting slot")) {
			this.setErrorId("302");
		} else if (errors.contains("is not a valid ending slot")) {
			this.setErrorId("302");
		} else if (errors.contains("Sorry, this exceeds the limit at a time in this category")) {
			this.setErrorId("304");
		} else if (errors.contains("This booking is too close to a previous booking you have made. ")) {
			// This booking is too close to a previous booking you have made. This category
			// has a limit of 2 hours between bookings.
			this.setErrorId("305");
		}

	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

}
