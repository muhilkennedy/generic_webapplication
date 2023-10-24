package com.mken.itinerary.exception;

import com.platform.exception.CustomWrapperException;

/**
 * @author Muhil
 *
 */
public class DestinationException extends CustomWrapperException {

	private static final long serialVersionUID = 1L;

	public DestinationException() {
		super();
	}

	public DestinationException(String msg) {
		super(msg);
	}
}
