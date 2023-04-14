package com.mken.i18n.util;

import org.apache.commons.lang3.LocaleUtils;

public class Locale {

	public static java.util.Locale getValidLocale(String localeCode) {
		return LocaleUtils.toLocale(localeCode);
	}
	
}
