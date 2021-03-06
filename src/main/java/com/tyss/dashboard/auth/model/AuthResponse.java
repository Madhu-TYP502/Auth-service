package com.tyss.dashboard.auth.model;

import java.util.List;

public class AuthResponse {

	private String accessToken;
	private String refreshToken;
	private List<String> roles;

	public AuthResponse() {

	}

	public AuthResponse(String accessToken, String refreshToken,List<String> ROLES) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.roles = ROLES;

	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> rOLES) {
		roles = rOLES;
	}
	
	

}
