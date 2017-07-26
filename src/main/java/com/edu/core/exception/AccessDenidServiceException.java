package com.edu.core.exception;

public class AccessDenidServiceException extends ServiceException {
	public AccessDenidServiceException() {
		super();
	}

	public AccessDenidServiceException(String message) {
		super(message);
	}

	public AccessDenidServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDenidServiceException(Throwable cause) {
		super(cause);
	}

	public AccessDenidServiceException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public AccessDenidServiceException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.code = errorCode;
	}
}
