package com.saharak.sprintBootTemplate.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private final String tokenType = "Bearer";

	private final String jwtToken;

	public JwtResponse(final String jwtToken) {
		this.jwtToken = jwtToken;
	}

	@JsonProperty("tokenType")
	public String getTokenType() {
		return this.tokenType;
	}

	@JsonProperty("accessToken")
	public String getToken() {
		return this.jwtToken;
	}

	@JsonFormat(locale = "th", timezone = "Asia/Bangkok")
	public Date getTimestamp() {
		return new Date();
	}
}