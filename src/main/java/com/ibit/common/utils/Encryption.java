package com.ibit.common.utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class Encryption {
    public static String encryptPassword(String plainTextPassword, String encryptionKey) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(encryptionKey);
        return textEncryptor.encrypt(plainTextPassword);
    }

    public static String decryptPassword(String encryptedPassword, String encryptionKey) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(encryptionKey);
        return textEncryptor.decrypt(encryptedPassword);
    }
}
