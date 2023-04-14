package com.platform.exception;

/**
 * @author Muhil Extend this class for custom exceptions
 */
public abstract class CustomWrapperException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

	public CustomWrapperException() {
		super();
	}

	public CustomWrapperException(String message) {
		super(message);
		this.message = message;
	}

	public CustomWrapperException(String message, String errorCode) {
		super(message);
		this.message = message;
		this.errorCode = errorCode;
	}

	public CustomWrapperException(Throwable throwable) {
		super(throwable);
	}

	public CustomWrapperException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
