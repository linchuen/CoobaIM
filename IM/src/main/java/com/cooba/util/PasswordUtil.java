package com.cooba.util;

import com.cooba.constant.EncryptEnum;
import com.cooba.exception.BaseException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordUtil {
    public static String encrypt(String key, String data, EncryptEnum type) {
        if (key.length() != type.getKeySize()) throw new BaseException();

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), type.name());
            Cipher cipher = Cipher.getInstance(type.getInstance());
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new BaseException();
        }
    }

    public static String decrypt(String key, String encryptedData, EncryptEnum type) {
        if (key.length() != type.getKeySize()) throw new BaseException();

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), type.name());
            Cipher cipher = Cipher.getInstance(type.getInstance());
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] originalBytes = cipher.doFinal(decodedBytes);
            return new String(originalBytes);
        } catch (Exception e) {
            throw new BaseException();
        }
    }

    public static String hash(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());

            byte[] bytes = messageDigest.digest();
            return Hex.encodeHexString(bytes);
        } catch (Exception e) {
            throw new BaseException();
        }
    }

    public static void main(String[] args) {
        String key = "12345678abcdefgh";

        PasswordUtil passwordUtil = new PasswordUtil();
        String encrypted = passwordUtil.encrypt(key, "testData", EncryptEnum.AES);
        System.out.println("encrypted = " + encrypted);
        String decrypted = passwordUtil.decrypt(key, encrypted, EncryptEnum.AES);
        System.out.println("decrypted = " + decrypted);
    }
}
