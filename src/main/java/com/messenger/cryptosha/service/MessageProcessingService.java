package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatMessageDTO;

import java.util.List;

public interface MessageProcessingService {
    void saveChatMessage(ChatMessageDTO chatMessageDTO);

    List<ChatMessageDTO> getAllMessagesByChat(Long chatId);
}
