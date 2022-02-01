package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.exceptions.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    UserDTO getUserById(Long userId) throws NotFoundException;

    UserDTO createUser(String userName, String passwordHash);

    String getPassHash(Long userId);

    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;

    Long getUserId(String username);
}
