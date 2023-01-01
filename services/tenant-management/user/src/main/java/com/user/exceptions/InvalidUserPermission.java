package com.user.exceptions;

public class InvalidUserPermission extends Exception {

	public InvalidUserPermission() {
		super();
	}
	
	public InvalidUserPermission(String message) {
		super(message);
	}
}
