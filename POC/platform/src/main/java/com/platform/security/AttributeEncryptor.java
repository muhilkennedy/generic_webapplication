package com.platform.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.StringUtils;

import com.platform.util.PlatformPropertiesUtil;

/**
 * @author Muhil
 * Encryption Standard for DB field encryption and
 * decryption of entity - @Convert(converter = ValueAttributeConverter.class)
 *
 */
public class AttributeEncryptor implements AttributeConverter<String, String> {

	private static final String ALGORITHM_AES = "AES";

	private final Key key;
	private final Cipher cipher;

	public AttributeEncryptor() throws Exception {
		key = new SecretKeySpec(PlatformPropertiesUtil.getDbSecret().getBytes(), ALGORITHM_AES);
		try {
			cipher = Cipher.getInstance(ALGORITHM_AES);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String convertToDatabaseColumn(String rawData) {
		if (StringUtils.isAllBlank(rawData)) {
			return rawData;
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encodeToString(cipher.doFinal(rawData.getBytes()));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		if (StringUtils.isAllBlank(dbData)) {
			return dbData;
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static String encrypt(String key, String rawData) {
		Key generatedKey = new SecretKeySpec(key.getBytes(), ALGORITHM_AES);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
			cipher.init(Cipher.ENCRYPT_MODE, generatedKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(rawData.getBytes()));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static String decrypt(String key, String encryptedData) {
		Key generatedKey = new SecretKeySpec(key.getBytes(), ALGORITHM_AES);
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
			cipher.init(Cipher.DECRYPT_MODE, generatedKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}

