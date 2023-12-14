package com.example.aes256;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Base64;

@SpringBootApplication
public class Aes256Application {
	private static final String KEY = "4F8ABBD4EE68E655F42146E87D6E4022";
	private static final String AES_ALGORITHM = "AES";
	private static final String AES_MODE = "AES/ECB/NoPadding";

	public static void main(String[] args) {
		SpringApplication.run(Aes256Application.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			String encryptedText = encrypt(padString("01080036455"));
			System.out.println(MessageFormat.format("""
					암호문 : {0}""", encryptedText));

			String decryptedText = decrypt(encryptedText);
			System.out.println(MessageFormat.format("""
					복호문 : {0}""", decryptedText));
		};
	}

	String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), AES_ALGORITHM);
		Cipher cipher = Cipher.getInstance(AES_MODE);

		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), AES_ALGORITHM);
		Cipher cipher = Cipher.getInstance(AES_MODE);

		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

		int lastNonNullIndex = decryptedBytes.length - 1;
		while (lastNonNullIndex >= 0 && decryptedBytes[lastNonNullIndex] == 0) {
			lastNonNullIndex--;
		}

		byte[] resultBytes = Arrays.copyOf(decryptedBytes, lastNonNullIndex + 1);

		return new String(resultBytes);
	}

	String padString(String input) {
		int paddingLength = 16 - input.length() % 16;
		return input + "\0".repeat(paddingLength);
	}
}
