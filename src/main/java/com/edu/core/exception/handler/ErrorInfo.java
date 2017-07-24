package com.edu.core.exception.handler;

public class ErrorInfo <T> {
	public static final Integer OK = 0;
    public static final Integer ERROR = 500;
    private String status = "500";
    private String message;
    private T data;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
