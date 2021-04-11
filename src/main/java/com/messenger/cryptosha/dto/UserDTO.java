package com.messenger.cryptosha.dto;

import com.messenger.cryptosha.model.ChatModel;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private Set<ChatModel> chats;

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChats(Set<ChatModel> chats) {
        this.chats = chats;
    }

    public Set<ChatModel> getChats() {
        return chats;
    }
}
