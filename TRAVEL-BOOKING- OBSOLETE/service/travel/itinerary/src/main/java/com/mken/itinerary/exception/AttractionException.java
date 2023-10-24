package com.mken.itinerary.exception;

import com.platform.exception.CustomWrapperException;

/**
 * @author Muhil
 *
 */
public class AttractionException extends CustomWrapperException {

	private static final long serialVersionUID = 1L;

	public AttractionException(String message) {
		super(message);
	}
	
}
