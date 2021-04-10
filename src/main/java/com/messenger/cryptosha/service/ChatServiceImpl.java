package com.messenger.cryptosha.service;

import com.andreyka.crypto.KeyPair;
import com.messenger.cryptosha.persistence.ChatPersistence;
import org.springframework.stereotype.Service;

import javax.management.OperationsException;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatPersistence chatPersistence;

    public ChatServiceImpl(ChatPersistence chatPersistence) {
        this.chatPersistence = chatPersistence;
    }

    @Override
    public Long createChat(String chatName) throws OperationsException {
        KeyPair chatKeyPair;
        try {
            chatKeyPair = KeyPair.generateKeyPair();
        } catch (CloneNotSupportedException e) {
            throw new OperationsException("Something went wrong!");
        }
        return chatPersistence.createChat(chatName, chatKeyPair);
    }
}
