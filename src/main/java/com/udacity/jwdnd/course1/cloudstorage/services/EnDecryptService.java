package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

public class EnDecryptService {
    private EncryptionService encryptionService;
    private byte[] key = setSecureRandom();
    private String encodedKey = Base64.getEncoder().encodeToString(key);

    public EnDecryptService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public byte [] setSecureRandom(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return key;
    }

    public String encrypt(String password){
        return encryptionService.encryptValue(password, encodedKey);
    }

    public String decrypt(String encryptedPassword){
        return encryptionService.decryptValue(encryptedPassword, encodedKey);
    }

    public String getEncodedKey() {
        return encodedKey;
    }
}
