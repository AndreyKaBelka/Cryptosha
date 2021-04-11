package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestParam String username) {
        UserDTO userDTO = userService.createUser(username);
        return ResponseEntity.ok(Collections.singletonMap("userId", userDTO.getId()));
    }
}
