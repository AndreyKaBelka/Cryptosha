package com.messenger.cryptosha.persistence;

import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.repository.UserRepository;
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
        return userModel.orElse(null);
    }

    public UserModel createUser(String username) {
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        return userRepository.save(userModel);
    }
}
