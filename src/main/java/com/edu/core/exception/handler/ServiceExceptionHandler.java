package com.edu.core.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.core.exception.ServiceException;

@ControllerAdvice
public class ServiceExceptionHandler  {

	@ExceptionHandler(value = ServiceException.class)
	@ResponseBody
	public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, ServiceException e) {
		ErrorInfo<String> err = new ErrorInfo<>();
		err.setMessage(e.getMessage());
		err.setStatus(e.getCode());
		err.setData(e.getMessage());
		return err;
	}
}
