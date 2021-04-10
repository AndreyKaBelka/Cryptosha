package com.messenger.cryptosha.service;

import com.andreyka.crypto.ECPoint;
import com.messenger.cryptosha.dto.KeyDTO;
import com.messenger.cryptosha.model.KeyModel;
import com.messenger.cryptosha.persistence.KeyPersistence;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;

@Service
public class KeyServiceImpl implements KeyService {
    private final KeyPersistence keyPersistence;

    public KeyServiceImpl(KeyPersistence keyPersistence) {
        this.keyPersistence = keyPersistence;
    }

    @Override
    public KeyDTO getUserKeyDTO(Integer userId) {
        KeyModel keyModel = keyPersistence.getById(userId);
        return mapToKeyDTO(keyModel);
    }

    @Override
    public void setUserPublicKey(KeyDTO keyDTO) {
        KeyModel keyModel = mapToKeyModel(keyDTO);
        keyPersistence.save(keyModel);
    }

    private KeyModel mapToKeyModel(KeyDTO keyDTO) {
        KeyModel keyModel = new KeyModel();
        keyModel.setUserId(keyDTO.getUserId());
        keyModel.setPublicKey(keyDTO.getPublicKey().toString());

        return keyModel;
    }

    private KeyDTO mapToKeyDTO(KeyModel keyModel){
        KeyDTO keyDTO = new KeyDTO();
        try {
            keyDTO.setPublicKey(keyModel.getPublicKey());
            keyDTO.setUserId(keyModel.getUserId());
        } catch (EntityNotFoundException ex) {
            throw new InvalidParameterException("Wrong user id, try again");
        }
        return keyDTO;
    }
}
