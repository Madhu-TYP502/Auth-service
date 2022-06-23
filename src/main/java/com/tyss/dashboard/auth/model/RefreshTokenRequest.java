package com.tyss.dashboard.auth.model;

public class RefreshTokenRequest {

	String refreshToken;

	public RefreshTokenRequest(String refreshToken) {
		super();
		this.refreshToken = refreshToken;
	}

	public RefreshTokenRequest() {
		super();
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
}
