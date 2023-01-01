package com.base.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * @author Muhil Kennedy
 * Read Db field encryption keys from vault
 */
@Primary
@Qualifier(value="dbprops")
@ConfigurationProperties("app.db.encryption")
public class DatabaseFieldProperties {

	private String publicKey;
	private String privateKey;

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
