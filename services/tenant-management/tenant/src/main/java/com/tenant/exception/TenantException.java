package com.tenant.exception;

public class TenantException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public TenantException() {
		super();
	}
	
	public TenantException(String msg) {
		super(msg);
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}

}
