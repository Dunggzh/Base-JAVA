package com.its.base_java.util;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@Log4j2
public final class Aes {

    public static String encrypt(byte[] keyValue, String plaintext) throws Exception {
        Key key = new SecretKeySpec(keyValue, "AES");
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] iv = c.getIV();
        byte[] encVal = c.doFinal(plaintextBytes);
        String encryptedData = Base64.getEncoder().encodeToString(encVal);
        SecretKeySpec macKey = new SecretKeySpec(keyValue, "AES");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(macKey);
        hmacSha256.update(Base64.getEncoder().encode(iv));
        byte[] calcMac = hmacSha256.doFinal(encryptedData.getBytes(StandardCharsets.UTF_8));
        String mac = new String(Hex.encode(calcMac));
        System.out.println(mac);
        AesEncryptionData aesData = new AesEncryptionData(Base64.getEncoder().encodeToString(iv), encryptedData, mac);

        String aesDataJson = new Gson().toJson(aesData);

        return Base64.getEncoder().encodeToString(aesDataJson.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] keyValue, String ivValue, String encryptedData, String macValue) throws Exception {
        Key key = new SecretKeySpec(keyValue, "AES");
        byte[] iv = Base64.getDecoder().decode(ivValue.getBytes(StandardCharsets.UTF_8));
        byte[] decodedValue = Base64.getDecoder().decode(encryptedData.getBytes(StandardCharsets.UTF_8));

        SecretKeySpec macKey = new SecretKeySpec(keyValue, "AES");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(macKey);
        hmacSha256.update(ivValue.getBytes(StandardCharsets.UTF_8));
        byte[] calcMac = hmacSha256.doFinal(encryptedData.getBytes(StandardCharsets.UTF_8));
        byte[] mac = Hex.decode(macValue);
        if (!Arrays.equals(calcMac, mac)) return "MAC mismatch";

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding"); // or PKCS5Padding
        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decValue = c.doFinal(decodedValue);

        int firstQuoteIndex = 0;
        return new String(Arrays.copyOfRange(decValue, firstQuoteIndex, decValue.length));
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class AesEncryptionData {
        public String iv;
        public String value;
        public String mac;
    }

}
