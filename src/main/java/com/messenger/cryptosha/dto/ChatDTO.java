package com.messenger.cryptosha.dto;

import com.andreyka.crypto.ECPoint;
import com.messenger.cryptosha.model.UserModel;

import java.util.Set;

public class ChatDTO {
    private Long id;
    private String privateKey;
    private ECPoint publicKey;
    private String chatName;
    private Set<UserModel> usersInChat;

    public ChatDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
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

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = ECPoint.parseValue(publicKey);
    }
}
