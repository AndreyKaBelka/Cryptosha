package com.messenger.cryptosha.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    private String username;

    @ManyToMany(mappedBy = "users")
    private Set<ChatModel> chats = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<ChatModel> getChats() {
        return chats;
    }

    public void setChats(Set<ChatModel> chats) {
        this.chats = chats;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
