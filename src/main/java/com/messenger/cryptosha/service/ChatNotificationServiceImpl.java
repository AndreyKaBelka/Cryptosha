package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.model.ChatNotificationModel;
import com.messenger.cryptosha.persistence.ChatNotificationPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChatNotificationServiceImpl implements ChatNotificationService {
    private final ChatNotificationPersistence chatNotificationPersistence;

    @Autowired
    public ChatNotificationServiceImpl(ChatNotificationPersistence chatNotificationPersistence) {
        this.chatNotificationPersistence = chatNotificationPersistence;
    }

    @Override
    public ChatNotificationDTO addNotification(Long userId, Long chatId, Long messageId) {
        ChatNotificationModel model = chatNotificationPersistence.save(chatId, userId, messageId);
        return mapToDTO(model);
    }

    @Override
    public void deleteNotification(Long userId, Long chatId) {
        chatNotificationPersistence.deleteNotificationFor(userId, chatId);
    }

    @Override
    public ChatNotificationDTO mapToDTO(ChatNotificationModel chatNotificationModel) {
        ChatNotificationDTO chatNotificationDTO = new ChatNotificationDTO();
        chatNotificationDTO.setChatId(chatNotificationModel.getChatId());
        chatNotificationDTO.setUserId(chatNotificationModel.getUserId());
        chatNotificationDTO.setId(chatNotificationModel.getId());
        chatNotificationDTO.setMessageStatus(chatNotificationModel.getMessageStatus());
        chatNotificationDTO.setMessageId(chatNotificationModel.getMessageId());
        return chatNotificationDTO;
    }

    @Override
    public List<ChatNotificationDTO> getNotificationsForUserAndChat(Long userId, Long chatId) {
        List<ChatNotificationModel> models = chatNotificationPersistence.getNotifications(userId, chatId);
        return models.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
