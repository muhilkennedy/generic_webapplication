package com.base.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Muhil Kennedy
 * Read from vault
 */
@Qualifier(value = "dbprops")
@ConfigurationProperties("app.jwt.encryption")
public class JWTProperties {

	private String secret;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
