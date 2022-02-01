package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.exceptions.NotFoundException;
import com.messenger.cryptosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> json, HttpServletResponse response) {
        UserDTO userDTO = userService.createUser(json.get("username"), json.get("passwordHash"));
        Cookie userIdCookie = new Cookie("userId", userDTO.getId().toString());
        userIdCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(userIdCookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public UserDTO getUser(@RequestParam Long userId) throws NotFoundException {
        return userService.getUserById(userId);
    }
}
