package com.messenger.cryptosha.persistence;

import com.messenger.cryptosha.MessageStatus;
import com.messenger.cryptosha.model.ChatNotificationModel;
import com.messenger.cryptosha.repository.ChatNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationPersistence {
    private final ChatNotificationRepository chatNotificationRepository;

    @Autowired
    public ChatNotificationPersistence(ChatNotificationRepository chatNotificationRepository) {
        this.chatNotificationRepository = chatNotificationRepository;
    }

    public ChatNotificationModel save(Long chatId, Long userId) {
        ChatNotificationModel model = new ChatNotificationModel();
        model.setChatId(chatId);
        model.setUserId(userId);
        model.setMessageStatus(MessageStatus.DELIVERED);
        return chatNotificationRepository.saveAndFlush(model);
    }

    public Long getNotificationCountForChatAndUser(Long chatId, Long userId) {
        ChatNotificationModel chatNotificationModel = new ChatNotificationModel();
        chatNotificationModel.setChatId(chatId);
        chatNotificationModel.setUserId(userId);
        return chatNotificationRepository.count(Example.of(chatNotificationModel));
    }
}
