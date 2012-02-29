package com.rest.example.services;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class CryptoMan {
	public static CryptoMan me = new CryptoMan();

	public static CryptoMan getInstance() {

		return me;
	}

	private CryptoMan() {
		// add the security provider
		// not required if you have Install the library
		// by Configuring the Java Runtime
		Security.addProvider(new BouncyCastleProvider());

	}

	public byte[] decryptDES_CBC_WDE(byte[] data) {
		// DES only accept encryption key with
		// 8 bytes length only
		byte[] encryptionKey = { (byte) 0xBC, 0x47, 0x40, 0x48, (byte) 0xA3,
				0x3B, 0x48, 0x3A };
		byte[] initializationVector = { 0x63, 0x68, 0x72, 0x79, 0x73, 0x31,
				0x33, 0x72 };
		return decryptDES_CBC(encryptionKey, initializationVector, data);
	}

	private byte[] decryptDES_CBC(final byte[] encryptionKey,
			final byte[] initializationVector, final byte[] input) {
		try {
			SecretKeySpec key = new SecretKeySpec(encryptionKey, "DES");
			// set the iv psec
			IvParameterSpec ivSpec = new IvParameterSpec(initializationVector);
			// get the cipher instance
			Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding", "BC");
			// decrypt it back
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			// get the output text (decrypted text
			byte[] decryptedText = new byte[cipher.getOutputSize(input.length)];

			int ctLength = cipher.update(input, 0, input.length, decryptedText,
					0);
			ctLength += cipher.doFinal(decryptedText, ctLength);

			return decryptedText;
		} catch (Exception e) {
			// convert to runtime
			throw new RuntimeException(e);
		}
	}

	public void doIt() {

		// DES only accept encryption key with
		// 8 bytes length only
		byte[] EncryptionKey = { 0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78 };

		// DES without padding must contains data length n * 8
		byte[] input = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x11,
				0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18 };

		byte[] iv = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

		// show the input to the screen
		System.out.println("Input: " + new String(Hex.encode(input)));

		SecretKeySpec key = new SecretKeySpec(EncryptionKey, "DES");

		try {
			// set the iv psec
			IvParameterSpec ivSpec = new IvParameterSpec(iv);

			// get the cipher instance
			Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding", "BC");

			// init the cipher
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

			// get the output length
			byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

			int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
			ctLength += cipher.doFinal(cipherText, ctLength);

			// show the encrypted text to the screen
			System.out.println("Encrypted Input: "
					+ new String(Hex.encode(cipherText)));

			// decrypt it back
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

			// get the output text (decrypted text
			byte[] decryptedText = new byte[cipher.getOutputSize(input.length)];

			ctLength = cipher.update(cipherText, 0, cipherText.length,
					decryptedText, 0);
			ctLength += cipher.doFinal(decryptedText, ctLength);

			// show the decrypted text to the screen
			System.out.println("Decrypted Input: "
					+ new String(Hex.encode(decryptedText)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
