package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long userId);

    UserDTO createUser(String userName);
}
