package com.its.base_java.component.converter;

import com.google.gson.Gson;
import com.its.base_java.util.Aes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@Converter
public class DataSensitiveConverter implements AttributeConverter<String, String> {

    @Value("${app.data-sensitive.secret-key}")
    private String secretKey;

    @Override
    public String convertToDatabaseColumn(String s) {
        try {
            return Aes.encrypt(secretKey.getBytes(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String encrypt) {
        String content = new String(Base64.getDecoder().decode(encrypt));
        Gson gson = new Gson();
        Aes.AesEncryptionData encryptionData = gson.fromJson(content, Aes.AesEncryptionData.class);
        try {
            return Aes.decrypt(secretKey.getBytes(), encryptionData.iv, encryptionData.value, encryptionData.mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
