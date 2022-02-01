package com.messenger.cryptosha.dto;

import com.andreyka.crypto.api.ECPoint;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ChatDTO {
    private Long id;
    private String privateKey;
    private ECPoint publicKey;
    private String chatName;
    private Set<Long> usersIdInChat;

    public void setPublicKey(String publicKey) {
        this.publicKey = ECPoint.parseValue(publicKey);
    }
}
