package com.saharak.sprintBootTemplate.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	public JwtRequest() {
	}

	public JwtRequest(final String username, final String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}