package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public class ErrorDetails {

	private String errorId;
	private String errorString;

	@JsonCreator
	public ErrorDetails(String errorString) {

		this.setErrorString(errorString);

	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errors) {

		
		System.out.println("errors" + errors);
		
		int firstBracket = errors.indexOf('{');

		String error = errors.substring(firstBracket + 1, errors.indexOf('}', firstBracket));
		
		System.out.println("error" + error);
		
		
		String contentOfBrackets = "{" + errors.substring(firstBracket + 1, errors.indexOf('}', firstBracket)) + "}";

		Gson g = new Gson();

		System.out.println("contentOfBrackets" + contentOfBrackets);

		Error p = g.fromJson(contentOfBrackets, Error.class);

		char[] chars = p.getErrors().toCharArray();
		
		StringBuilder sb = new StringBuilder();
		
		for (char c : chars) {
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}

		this.setErrorId(sb.toString());
		
		errorString = p.getErrors();
	}


	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

}

final class Error {
	private String errors;

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

}