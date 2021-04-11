package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.model.ChatNotificationModel;
import com.messenger.cryptosha.persistence.ChatNotificationPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationServiceImpl implements ChatNotificationService {
    private final ChatNotificationPersistence chatNotificationPersistence;

    @Autowired
    public ChatNotificationServiceImpl(ChatNotificationPersistence chatNotificationPersistence) {
        this.chatNotificationPersistence = chatNotificationPersistence;
    }

    @Override
    public ChatNotificationDTO addNotification(Long userId, Long chatId) {
        ChatNotificationModel model = chatNotificationPersistence.save(chatId, userId);
        return mapToDTO(model);
    }

    @Override
    public ChatNotificationDTO mapToDTO(ChatNotificationModel chatNotificationModel) {
        ChatNotificationDTO chatNotificationDTO = new ChatNotificationDTO();
        chatNotificationDTO.setChatId(chatNotificationModel.getChatId());
        chatNotificationDTO.setUserId(chatNotificationModel.getUserId());
        chatNotificationDTO.setId(chatNotificationModel.getId());
        chatNotificationDTO.setMessageStatus(chatNotificationModel.getMessageStatus());
        return chatNotificationDTO;
    }
}
