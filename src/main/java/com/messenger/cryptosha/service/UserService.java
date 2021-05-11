package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.UserDTO;
import javassist.NotFoundException;

public interface UserService {
    UserDTO getUserById(Long userId) throws NotFoundException;

    UserDTO createUser(String userName);
}
