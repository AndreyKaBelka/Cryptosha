package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.model.ChatModel;
import javassist.NotFoundException;

import javax.management.OperationsException;
import java.util.Set;

public interface ChatService {
    ChatDTO createChat(String chatName) throws OperationsException;

    ChatDTO addUserToChat(Long chatId, Long userId) throws NotFoundException;

    ChatDTO getChatById(Long chatId) throws NotFoundException;

    boolean isUserConnected(Long chatId, Long userId) throws NotFoundException;

    Long[] getChatUserIds(Long chatId);

    Set<ChatDTO> getAllChatsForUser(Long userId);
}
