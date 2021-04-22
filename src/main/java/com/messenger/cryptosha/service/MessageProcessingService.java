package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatMessageDTO;

import java.util.List;

public interface MessageProcessingService {
    Long saveChatMessage(ChatMessageDTO chatMessageDTO);

    List<ChatMessageDTO> getAllMessagesByChat(Long chatId);

    List<ChatMessageDTO> getUnreadMessagesForUserAndChat(Long userId, Long chatId);
}
