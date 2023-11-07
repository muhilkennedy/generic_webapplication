package com.mken.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Muhil
 *
 */
public class Log {
	
	public static enum loggers { base, itinerary, user, i18n, service };

	public static Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static final Logger base = LoggerFactory.getLogger("com.mken.base");
	public static final Logger service = LoggerFactory.getLogger("com.mken.service");
	public static final Logger itinerary = LoggerFactory.getLogger("com.mken.itinerary");
	public static final Logger user = LoggerFactory.getLogger("com.mken.user");
	public static final Logger i18n = LoggerFactory.getLogger("com.mken.i18n");

}

