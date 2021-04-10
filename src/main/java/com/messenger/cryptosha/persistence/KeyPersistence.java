package com.messenger.cryptosha.persistence;

import com.messenger.cryptosha.model.KeyModel;
import com.messenger.cryptosha.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyPersistence {
    private final KeyRepository keyRepository;

    @Autowired
    public KeyPersistence(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public void save(KeyModel keyModel) {
        keyRepository.save(keyModel);
    }

    public List<KeyModel> getAll() {
        return keyRepository.findAll();
    }

    public KeyModel getById(Integer userId) {
        return keyRepository.getOne(userId);
    }
}
