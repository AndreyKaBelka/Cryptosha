package com.messenger.cryptosha.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "chat")
public class ChatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String privateKey;

    private String publicKey;

    private String chatName;

    @ManyToMany
    private Set<UserModel> usersInChat;

    public Long getId() {
        return id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Set<UserModel> getUsersInChat() {
        return usersInChat;
    }

    public void setUsersInChat(Set<UserModel> usersInChat) {
        this.usersInChat = usersInChat;
    }
}
