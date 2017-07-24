package com.edu.core.exception.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorInfo<List<ObjectError>> jsonErrorHandler(HttpServletRequest req, MethodArgumentNotValidException e) throws Exception {
        ErrorInfo<List<ObjectError>> err = new ErrorInfo<>();
        err.setMessage("数据校验失败！");
        err.setStatus("406");
        err.setData(e.getBindingResult().getAllErrors());
        return err;
    }
}
