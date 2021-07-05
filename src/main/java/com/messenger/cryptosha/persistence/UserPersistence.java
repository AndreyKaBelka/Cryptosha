package com.messenger.cryptosha.persistence;

import com.messenger.cryptosha.exceptions.UserNotFoundException;
import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.repository.UserRepository;
import org.h2.engine.User;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPersistence {
    private final UserRepository userRepository;

    public UserPersistence(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUserById(Long userId) {
        Optional<UserModel> userModel = userRepository.findById(userId);
        return userModel.orElseThrow(UserNotFoundException::new);
    }

    public UserModel createUser(String username, String passwordHash) {
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setPasswordHash(passwordHash);
        return userRepository.save(userModel);
    }

    public UserModel getUserByUsername(String username) {
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        return userRepository.findOne(Example.of(userModel)).orElseThrow(UserNotFoundException::new);
    }
}
