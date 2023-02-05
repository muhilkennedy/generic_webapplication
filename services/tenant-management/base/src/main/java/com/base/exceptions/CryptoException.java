package com.base.exceptions;

/**
 * @author Muhil
 *
 */
public class CryptoException extends Exception {

	public CryptoException(String msg) {
		super(msg);
	}
	
	public CryptoException(Exception ex) {
		super(ex);
	}
	
}
