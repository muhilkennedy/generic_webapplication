package com.core.advice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.base.util.GenericResponse;
import com.base.util.Response;

/**
 * @author Muhil Kennedy
 * Global controller errorhandling
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public GenericResponse<Map> handleRuntimeException(RuntimeException ex, WebRequest request) {
		GenericResponse<Map> response = new GenericResponse<Map>();
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		if(logger.isDebugEnabled()) {
			ex.printStackTrace();
		}
		response.setErrorMessages(Arrays.asList(ex.getMessage()));
		logger.error("handleRuntimeException :: Exception :: " + ex.getMessage());
		return response.setStatus(Response.Status.ERROR).setData(body).build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, WebRequest request) {
		//GenericResponse<Map> response = new GenericResponse<Map>();
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		if(logger.isDebugEnabled()) {
			ex.printStackTrace();
		}
		//response.setErrorMessages(Arrays.asList(ex.getMessage()));
		logger.error("handleGenericException :: Exception :: " + ex.getMessage());
		return new ResponseEntity<Map<String, Object>>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
