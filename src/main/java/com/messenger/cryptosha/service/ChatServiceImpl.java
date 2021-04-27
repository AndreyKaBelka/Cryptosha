package com.messenger.cryptosha.service;

import com.andreyka.crypto.KeyPair;
import com.messenger.cryptosha.ChatTransformer;
import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.model.ChatModel;
import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.persistence.ChatPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.OperationsException;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatPersistence chatPersistence;
    private final UserService userService;
    private final ChatTransformer chatTransformer;

    @Autowired
    public ChatServiceImpl(ChatPersistence chatPersistence, UserService userService, ChatTransformer chatTransformer) {
        this.chatPersistence = chatPersistence;
        this.userService = userService;
        this.chatTransformer = chatTransformer;
    }

    @Override
    public ChatDTO createChat(String chatName) throws OperationsException {
        KeyPair chatKeyPair;
        try {
            chatKeyPair = KeyPair.generateKeyPair();
        } catch (CloneNotSupportedException e) {
            throw new OperationsException("Something went wrong!");
        }
        return chatTransformer.mapToDTO(chatPersistence.createChat(chatName, chatKeyPair));
    }

    @Override
    public ChatDTO addUserToChat(Long chatId, Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        return chatTransformer.mapToDTO(chatPersistence.addUserToChat(chatId, chatTransformer.mapToModel(userDTO)));
    }

    @Transactional
    @Override
    public Long[] getChatUserIds(Long chatId) {
        ChatModel chatModel = chatPersistence.getChatById(chatId);
        return chatModel
                .getUsers()
                .stream()
                .map(UserModel::getUserId)
                .toArray(Long[]::new);
    }

}
