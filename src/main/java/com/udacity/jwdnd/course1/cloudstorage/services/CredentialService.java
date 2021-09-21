package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    private User currentUser;

    public CredentialService(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredential(CredentialForm CredentialForm, Authentication authentication){
        Credential credential = new Credential();
        credential.setUrl(CredentialForm.getCredentialUrl());
        credential.setUsername(CredentialForm.getCredentialUsername());
        credential.setKey(encryptionService.getSalt());
        String encryptedPassword = encryptionService.encryptValue(CredentialForm.getCredentialPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        currentUser = userService.getUserByUsername(authentication.getName());
        credential.setUserid(currentUser.getUserid());
        credentialMapper.insertCred(credential);
    }

    public List<Credential> getCredentialsByUser(User user) {
        return credentialMapper.getCredentialByUserid(user.getUserid());
    }

    public List<Credential> getAllCredentials(Authentication authentication){
        currentUser = userService.getUserByUsername(authentication.getName());
        return credentialMapper.getCredentialByUserid(currentUser.getUserid());
    }

    public List<Credential> getAllCredentialsWithoutId(){
        return credentialMapper.getCredentials();
    }

    public void deleteCredential(Integer userid, Integer credentialid){
        Integer credentialById = credentialMapper.getCredByid(credentialid).getCredentialid();
        if(userid.equals(credentialMapper.getCredByid(credentialid).getUserid())){
            credentialMapper.deleteCred(credentialById);
        } else{
            throw new RuntimeException("There was an error while processing this request...");
        }
    }

   // public void updateCredential(String newUrl, String newUsername, String newPassword, Integer credentialId){
   //     String password = encryptionService.encryptValue(newPassword, encryptionService.getSalt());
   //     int updatedCredential = credentialMapper.updateCredential(newUrl,newUsername,password, credentialId);
   //     System.out.println("Updated " + updatedCredential + " credentials");
   // }
    public void updateCredential(String newUrl, String newUsername, String newPassword, Integer credentialId){
        Credential credential = credentialMapper.getCredByid(credentialId);
        String password = encryptionService.encryptValue(newPassword,credential.getKey());
        int updatedCredential = credentialMapper.updateCredential(newUrl,newUsername,password, credentialId);
        System.out.println("Updated " + updatedCredential + " credentials");
    }
}
