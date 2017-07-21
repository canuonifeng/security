package com.edu.core.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ThrowableHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorInfo<String> serviceExceptionHandler(HttpServletRequest req, Throwable e) throws Exception {
		ErrorInfo<String> err = new ErrorInfo<>();
        err.setMessage("服务器异常");
        err.setStatus("500");
        err.setData(e.getMessage());
        return err;
    }
}
