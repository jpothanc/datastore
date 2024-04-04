package com.ibit.datastore.helpers;

import com.ibit.datastore.models.QueryRequest;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CatalogueHelper {


    @Autowired
    private Environment environment;

    public static String getCatalogueKey(QueryRequest request) {
        return getCatalogueKey(request.getCatalogue(), request.getCatalogueItem());
    }

    public static String getCatalogueKey(String catalogue, String catalogueItem) {
        return new StringBuilder().append(catalogue).append(Constants.KEY_SEP).append(catalogueItem).toString();
    }

    public static String formattedTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)).toString();
    }

    public static String formattedDayStamp() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DAY_FORMAT)).toString();
    }


    public static String generateCacheKey(Object... params) {
        var keyBuilder = new StringBuilder("ds:").append(formattedDayStamp()).append(Constants.KEY_SEP);
        for (var arg : params) {
            keyBuilder.append(arg.toString().toLowerCase());  
            keyBuilder.append(Constants.KEY_SEP);
        }
        return keyBuilder.toString();
    }

    public static String getPasswordEncryptionKey() {
        return System.getenv(Constants.PASSWORD_ENCRYPTION_KEY);
    }

    public static String decryptPassword(String password, String encryptionKey) throws RuntimeException {
        if (encryptionKey == null || encryptionKey == "")
            throw new RuntimeException(Constants.PASSWORD_ENCRYPTION_KEY_ERROR_MSG);
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword(encryptionKey);
        if(password.startsWith("ENC"))
            password = password.substring(3);
        return encryptor.decrypt(password);
    }
}
