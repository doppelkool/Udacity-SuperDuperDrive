package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final EncryptionService encryptionService;

    public UserService(UserMapper userMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        String salt = encryptionService.getSalt();
        String hashedPassword = encryptionService.encryptValue(user.getPassword(), salt);
        return userMapper.insertUser(user.getUsername(), salt, hashedPassword, user.getFirstname(), user.getLastname());
    }

    public User getUserByUsername(String username) {
        return userMapper.getUser(username);
    }
}
