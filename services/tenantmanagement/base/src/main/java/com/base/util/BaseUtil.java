package com.base.util;

import java.util.Arrays;

/**
 * @author Muhil
 *
 */
public class BaseUtil {
	
	public static final int saltRounds = 5;
	private static final int randomCodeLength = 6;
	private static final int randomPasswordLength = 8;
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
	private static final String ALPHA_NUMERIC_SPECIAL_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz!@#$*";
	private static final String NUMERIC_STRING = "1234567890";
	
	/**
	 * @return random password with pre-defined length and Alpha numeric characters.
	 */
	public static String generateRandomPassword() {
		StringBuilder builder = new StringBuilder();
		int count = randomPasswordLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_SPECIAL_STRING.length());
			builder.append(ALPHA_NUMERIC_SPECIAL_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String generateRandomCode() {
		StringBuilder builder = new StringBuilder();
		int count = randomCodeLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String generateRandomNumericCode() {
		StringBuilder builder = new StringBuilder();
		int count = randomCodeLength;
		while (count-- != 0) {
			int character = (int) (Math.random() * NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static String[] getEnumAsString(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}

}
