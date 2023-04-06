package com.user.exceptions;

/**
 * @author muhil
 *
 */
public class InvalidUserPermission extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidUserPermission() {
		super();
	}
	
	public InvalidUserPermission(String message) {
		super(message);
	}
}
