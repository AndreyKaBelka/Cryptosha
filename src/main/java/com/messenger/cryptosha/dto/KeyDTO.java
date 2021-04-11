package com.messenger.cryptosha.dto;

import com.andreyka.crypto.ECPoint;

public class KeyDTO {
    Long userId;
    ECPoint publicKey;

    public KeyDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = ECPoint.parseValue(publicKey);
    }
}
