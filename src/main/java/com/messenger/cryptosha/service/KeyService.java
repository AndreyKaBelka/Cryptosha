package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.KeyDTO;

public interface KeyService {
    KeyDTO getUserKeyDTO(Integer userId);

    void setUserPublicKey(KeyDTO keyDTO);
}
