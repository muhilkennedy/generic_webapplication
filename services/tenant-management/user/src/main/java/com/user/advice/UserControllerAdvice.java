package com.user.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.base.messages.ErrorResponse;
import com.base.util.Log;
import com.user.exceptions.InvalidUserPermission;

/**
 * @author Muhil Kennedy
 * Global controller errorhandling
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserControllerAdvice {
	
	@ExceptionHandler(InvalidUserPermission.class)
	public ResponseEntity<ErrorResponse> handerUserPermisionException(InvalidUserPermission ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
		Log.core.error(errorResponse.toString());
		if (Log.user.isDebugEnabled()) {
			ex.printStackTrace();
		}
		Log.user.error("handerUserPermisionException :: Exception :: " + ex.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}


}
