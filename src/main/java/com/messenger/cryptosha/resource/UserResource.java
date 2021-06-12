package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.NotFoundException;
import com.messenger.cryptosha.dto.KeyDTO;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.service.KeyService;
import com.messenger.cryptosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> json, HttpServletResponse response) {
        UserDTO userDTO = userService.createUser(json.get("username"));
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setUserId(userDTO.getId());
        keyDTO.setPublicKey(json.get("publicKey"));
        keyService.setUserPublicKey(keyDTO);
        Cookie userIdCookie = new Cookie("userId", userDTO.getId().toString());
        userIdCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(userIdCookie);
        return ResponseEntity.ok(Collections.singletonMap("userId", userDTO.getId()));
    }

    @GetMapping
    public UserDTO getUser(@RequestParam Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }
}
