package com.messenger.cryptosha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserPublicKey")
public class KeyModel {
    private Long userId;
    private String publicKey;

    @Id
    @Column(name = "user_id", unique = true)
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "public_key", unique = true)
    public String getPublicKey() {
        return publicKey;
    }
    public void setPublicKey(String userPublicKey) {
        this.publicKey = userPublicKey;
    }
}
