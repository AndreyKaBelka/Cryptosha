package com.messenger.cryptosha.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private Set<Long> chatsId;

}
