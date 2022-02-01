package com.messenger.cryptosha.dto;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private Set<Long> chatsId;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Long> getChats() {
        return chatsId;
    }

    public void setChats(Set<Long> chatsId) {
        this.chatsId = chatsId;
    }
}
