package com.tenant.util;


/**
 * @author Muhil
 * Localization keys
 */
public enum TenantMessageKeys {

	INACTVE("tenant.inactive"), INVALID("tenant.invalid");

	String key;

	TenantMessageKeys(String code) {
		this.key = code;
	}

	public String getKey() {
		return key;
	}

}
