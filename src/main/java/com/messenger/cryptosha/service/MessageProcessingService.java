package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatMessageDTO;

public interface MessageProcessingService {
    void saveChatMessage(ChatMessageDTO chatMessageDTO);
}
