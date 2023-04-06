package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Muhil
 *
 */
public class EnvPropertiesUtil {
	
	public static String getEnvironmentValue(final String key) {
		String value = System.getenv(key);
		Log.base.debug("Requested for Key - {} :: value - {}", key, value);
		Assert.isTrue(!StringUtils.isAllBlank(value), "ENVIROMENT_VARIABLE " + key + " NOT_DEFINED");
		return value;
	}

}
