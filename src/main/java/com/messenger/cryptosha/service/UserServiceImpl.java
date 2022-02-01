package com.messenger.cryptosha.service;

import com.messenger.cryptosha.ChatTransformer;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.exceptions.InvalidJwtAuthenticationException;
import com.messenger.cryptosha.exceptions.NotFoundException;
import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private final UserPersistence userPersistence;
    private final ChatTransformer chatTransformer;

    @Autowired
    public UserServiceImpl(UserPersistence userPersistence, ChatTransformer chatTransformer) {
        this.userPersistence = userPersistence;
        this.chatTransformer = chatTransformer;
    }

    @Override
    public UserDTO getUserById(Long userId) throws NotFoundException {
        UserModel userModel = userPersistence.getUserById(userId);
        if (userModel == null) {
            throw new NotFoundException(String.format("User with id %s not found", userId));
        }
        return chatTransformer.mapToDTO(userModel);
    }

    @Override
    public UserDTO createUser(String userName, String passwordHash) {
        UserModel userModel = userPersistence.createUser(userName, passwordHash);
        return chatTransformer.mapToDTO(userModel);
    }

    @Override
    public String getPassHash(Long userId) {
        return userPersistence.getUserById(userId).getPasswordHash();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = getByUsername(username);
        return new User(userModel.getUsername(), userModel.getPasswordHash(), true, true, true, true, Collections.emptySet());
    }

    @Override
    public Long getUserId(String username) {
        return userPersistence.getUserByUsername(username).getUserId();
    }

    private UserModel getByUsername(String username) {
        if (username == null || username.isEmpty())
            throw new InvalidJwtAuthenticationException("JWT token doesn't contain username field");
        return userPersistence.getUserByUsername(username);
    }
}
