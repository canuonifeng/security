package com.edu.core.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.edu.core.exception.ServiceException;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ErrorInfo handleControllerException(HttpServletRequest req, Throwable ex) {

		if (ex instanceof ServiceException) {
			return serviceExceptionHandler(req, (ServiceException) ex);
		} else {
			ErrorInfo<String> err = new ErrorInfo<>();
	        err.setMessage("服务器异常");
	        err.setStatus("500");
	        err.setData(ex.getMessage());
	        return err;
		}
	}

	@ExceptionHandler(value = ServiceException.class)
	@ResponseBody
	public ErrorInfo serviceExceptionHandler(HttpServletRequest req, ServiceException e) {
		ErrorInfo<String> err = new ErrorInfo<>();
		err.setMessage(e.getMessage());
		err.setStatus(e.getCode());
		err.setData(e.getMessage());
		return err;
	}
}
