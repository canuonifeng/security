package com.edu.core;

import java.io.Serializable;

public class ResponseWrapper implements Serializable {

	private final Object body;
	private String status = "200";

	public ResponseWrapper(Object body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public Object getBody() {
		return body;
	}
}