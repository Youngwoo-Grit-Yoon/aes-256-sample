package com.example.aes256.utility;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class Aes256 {
    private static final String KEY = "4F8ABBD4EE68E655F42146E87D6E4022";
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_MODE = "AES/ECB/NoPadding";

    public static String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_MODE);

        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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

    public static String padString(String input) {
        int paddingLength = 16 - input.length() % 16;
        return input + "\0".repeat(paddingLength);
    }
}
