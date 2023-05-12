package com.platform.util;

import org.apache.commons.text.StringEscapeUtils;

public class SecurityUtil {
	
	public static String sanitizeString(String value) {
		return StringEscapeUtils.escapeHtml4(value);
	}

}
