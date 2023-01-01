package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class EnvPropertiesUtil {
	
	public static String getEnvironmentValue(final String key) {
		String value = System.getenv(key);
		Assert.isTrue(StringUtils.isAllBlank(value), "ENVIROMENT_VARIABLE_" + key + "_NOT_DEFNINED");
		return value;
	}

}
