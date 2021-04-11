package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.KeyDTO;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.service.KeyService;
import com.messenger.cryptosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;
    private final KeyService keyService;

    @Autowired
    public UserResource(UserService userService, KeyService keyService) {
        this.userService = userService;
        this.keyService = keyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> json) {
        UserDTO userDTO = userService.createUser(json.get("username"));
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setUserId(userDTO.getId());
        keyDTO.setPublicKey(json.get("publicKey"));
        keyService.setUserPublicKey(keyDTO);
        return ResponseEntity.ok(Collections.singletonMap("userId", userDTO.getId()));
    }
}
