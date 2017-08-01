package com.edu.core;

import java.io.Serializable;

public class ResponseWrapper implements Serializable {

	private final Object body;
	private String status = "200";
	private String message = "";

	public ResponseWrapper(Object body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public Object getBody() {
		return body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}