package com.messenger.cryptosha.service;

import com.messenger.cryptosha.NotFoundException;
import com.messenger.cryptosha.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long userId) throws NotFoundException;

    UserDTO createUser(String userName);
}
