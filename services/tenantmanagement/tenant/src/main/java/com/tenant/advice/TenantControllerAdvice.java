package com.tenant.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.base.messages.ErrorResponse;
import com.base.util.Log;
import com.tenant.api.TenantController;
import com.tenant.exception.TenantException;

@ControllerAdvice(assignableTypes = { TenantController.class })
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantControllerAdvice {

	@ExceptionHandler(TenantException.class)
	public ResponseEntity<ErrorResponse> handleTenantException(TenantException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		Log.tenant.error(errorResponse.toString());
		if(Log.tenant.isDebugEnabled()) {
			ex.printStackTrace();
		}
		Log.tenant.error("handleTenantException :: Exception :: " + ex.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
}
