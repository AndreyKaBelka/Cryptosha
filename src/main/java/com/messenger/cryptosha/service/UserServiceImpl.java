package com.messenger.cryptosha.service;

import com.messenger.cryptosha.ChatTransformer;
import com.messenger.cryptosha.NotFoundException;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.persistence.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserDTO createUser(String userName) {
        UserModel userModel = userPersistence.createUser(userName);
        return chatTransformer.mapToDTO(userModel);
    }

}
