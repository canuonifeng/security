package com.edu.core.exception.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ValidationExceptionHandler {
	@ExceptionHandler(value = ValidationException.class)
	@ResponseBody
	public ErrorInfo<String> jsonErrorHandler(HttpServletRequest req, ValidationException e)
			throws Exception {
		ErrorInfo<String> err = new ErrorInfo<>();
		err.setMessage("数据校验失败！");
		err.setStatus("406");
		err.setData(e.getMessage());
		return err;
	}
}
