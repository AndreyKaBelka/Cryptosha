package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.model.ChatNotificationModel;

public interface ChatNotificationService {
    ChatNotificationDTO addNotification(Long userId, Long chatId);

    void deleteNotification(Long userId, Long chatId);

    ChatNotificationDTO mapToDTO(ChatNotificationModel chatNotificationModel);
}
