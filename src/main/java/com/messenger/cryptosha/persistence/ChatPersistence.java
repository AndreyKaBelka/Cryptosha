package com.messenger.cryptosha.persistence;

import com.andreyka.crypto.KeyPair;
import com.messenger.cryptosha.model.ChatModel;
import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ChatPersistence {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatPersistence(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Long createChat(String chatName, KeyPair keyPair) {
        ChatModel chatModel = new ChatModel();
        chatModel.setChatName(chatName);
        chatModel.setPublicKey(keyPair.getPublicKey().toString());
        chatModel.setPrivateKey(keyPair.getPrivateKey().toString());
        return chatRepository.saveAndFlush(chatModel).getId();
    }
}
