package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.model.ChatModel;

import javax.management.OperationsException;

public interface ChatService {
    ChatDTO createChat(String chatName) throws OperationsException;

    ChatDTO addUserToChat(Long chatId, Long userId);

    Long[] getChatUserIds(Long chatId);
}
