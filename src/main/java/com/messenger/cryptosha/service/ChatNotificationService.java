package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.model.ChatNotificationModel;

import java.util.List;

public interface ChatNotificationService {
    ChatNotificationDTO addNotification(Long userId, Long chatId, Long messageId);

    void deleteNotification(Long userId, Long chatId);

    ChatNotificationDTO mapToDTO(ChatNotificationModel chatNotificationModel);

    List<ChatNotificationDTO> getNotificationsForUserAndChat(Long userId, Long chatId);
}
