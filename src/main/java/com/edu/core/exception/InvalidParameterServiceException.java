package com.edu.core.exception;

public class InvalidParameterServiceException extends ServiceException {

	public InvalidParameterServiceException() {
		super();
	}

	public InvalidParameterServiceException(String message) {
		super(message);
	}

	public InvalidParameterServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidParameterServiceException(Throwable cause) {
		super(cause);
	}

	public InvalidParameterServiceException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public InvalidParameterServiceException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.code = errorCode;
	}

}
