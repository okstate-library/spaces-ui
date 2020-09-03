package com.okstatelibrary.spacesui.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessToken {
	private String accessToken;
	private String scope;
	private String tokenType;
	private int expiresIn;
	
	@JsonCreator
	public AccessToken(@JsonProperty("access_token") String access_token,
			@JsonProperty("expires_in") int expires_in,
			@JsonProperty("token_type") String token_type,
			@JsonProperty("scope") String scope) {
		this.setAccessToken(access_token);
		this.setExpiresIn(expires_in);
		this.setScope(scope);
		this.setTokenType(token_type);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}	
	
}