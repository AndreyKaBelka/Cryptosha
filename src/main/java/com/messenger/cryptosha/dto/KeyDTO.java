package com.messenger.cryptosha.dto;

import com.andreyka.crypto.ECPoint;

public class KeyDTO {
    Integer userId;
    ECPoint publicKey;

    public KeyDTO() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = ECPoint.parseValue(publicKey);
    }
}
