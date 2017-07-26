package com.edu.core.exception;

public class NotFoundServiceException extends ServiceException {
	public NotFoundServiceException() {
		super();
	}

	public NotFoundServiceException(String message) {
		super(message);
	}

	public NotFoundServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundServiceException(Throwable cause) {
		super(cause);
	}

	public NotFoundServiceException(String errorCode, String message) {
		super(message);
		this.code = errorCode;
	}

	public NotFoundServiceException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.code = errorCode;
	}
}
