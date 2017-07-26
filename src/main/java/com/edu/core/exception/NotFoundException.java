package com.edu.core.exception;

public class NotFoundException extends ServiceException {
	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public NotFoundException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.code = errorCode;
	}
}
