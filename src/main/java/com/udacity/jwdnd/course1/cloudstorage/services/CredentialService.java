package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper)
    {
        this.credentialMapper = credentialMapper;
    }

    public boolean isCredavailable(Integer credentialid) {
        return credentialMapper.getCredByid(credentialid) != null;
    }

    public int createCredential(Credential credential) {
        return credentialMapper.insertCred(credential);
    }

    public List<Credential> getCredentialsByUser(User user) {
        return credentialMapper.getCredentialByUserid(user.getUserid());
    }
}
