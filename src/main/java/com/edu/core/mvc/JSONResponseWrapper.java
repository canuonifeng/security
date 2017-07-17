package com.edu.core.mvc;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(basePackages = "com.edu.controller")
public class JSONResponseWrapper implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return new ResponseWrapper(body);
    }

    private class ResponseWrapper {
        
    	private final Object body;
        private String status = "200";
        
        public ResponseWrapper(Object body) {
            this.body = body;
        }
		
        public String getStatus() {
			return status;
		}
		
		public Object getBody() {
			return body;
		}
    }
}