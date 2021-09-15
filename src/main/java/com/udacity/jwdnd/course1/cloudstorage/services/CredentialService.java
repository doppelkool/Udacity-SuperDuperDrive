package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public boolean isCredavailable(Integer credentialid) {
        return credentialMapper.getCredByid(credentialid) != null;
    }

    public int createCredential(Credential credential) {
        String salt = encryptionService.getSalt();
        String hashedPassword = encryptionService.encryptValue(credential.getPassword(), salt);

        credential.setKey(salt);
        credential.setPassword(hashedPassword);

        return credentialMapper.insertCred(credential);
    }

    public List<Credential> getCredentialsByUser(User user) {
        return credentialMapper.getCredentialByUserid(user.getUserid());
    }
}
