package com.base.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.base.exceptions.CryptoException;

/**
 * @author Muhil
 *
 */
public class EncryptionUtil {
	
	private static final String KEY_SECRET_ARG = "config.encryption.secret";
	
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";
	private static String SECRET; 
	
	public static void loadEncryptionKey(String keyValue){
		SECRET = keyValue;
	}

	public static void encrypt(String key, String value)
			throws CryptoException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		doEncrypt(key, value);
	}
	
	public static String doEncrypt(String key, String value)
			throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] input = value.getBytes();
		cipher.update(input);
		// encrypting the data
		byte[] cipherText = cipher.doFinal();
		System.out.println(new String(cipherText, "UTF8"));
		return new String(cipherText, "UTF8");
	}
	
	public static void decrypt(String key, String value)
			throws CryptoException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		doDecrypt(key, value);
	}
	
	public static String doDecrypt(String key, String value)
			throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedText = cipher.doFinal();
		System.out.println(new String(decryptedText, "UTF8"));
		return new String(decryptedText, "UTF8");
	}
	
    
	/*public static KeyPair generateKeypair() throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

		// Initializing the key pair generator
		keyPairGen.initialize(2048);

		// Generate the pair of keys
		KeyPair pair = keyPairGen.generateKeyPair();

		// Getting the public key from the key pair
		PublicKey publicKey = pair.getPublic();

		PrivateKey privatekey = pair.getPrivate();

		// Creating a Cipher object
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

		// Initializing a Cipher object
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		// Add data to the cipher
		byte[] input = "Welcome to Tutorialspoint".getBytes();
		cipher.update(input);

		// encrypting the data
		byte[] cipherText = cipher.doFinal();
		System.out.println(new String(cipherText, "UTF8"));

		// Initializing the same cipher for decryption
		cipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

		// Decrypting the text
		byte[] decipheredText = cipher.doFinal(cipherText);
		System.out.println(new String(decipheredText));

		return pair;
	}*/

}
