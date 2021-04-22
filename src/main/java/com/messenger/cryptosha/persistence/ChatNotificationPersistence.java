package com.messenger.cryptosha.persistence;

import com.messenger.cryptosha.MessageStatus;
import com.messenger.cryptosha.model.ChatNotificationModel;
import com.messenger.cryptosha.repository.ChatNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatNotificationPersistence {
    private final ChatNotificationRepository chatNotificationRepository;

    @Autowired
    public ChatNotificationPersistence(ChatNotificationRepository chatNotificationRepository) {
        this.chatNotificationRepository = chatNotificationRepository;
    }

    public ChatNotificationModel save(Long chatId, Long userId, Long messageId) {
        ChatNotificationModel model = new ChatNotificationModel();
        model.setChatId(chatId);
        model.setUserId(userId);
        model.setMessageStatus(MessageStatus.DELIVERED);
        model.setMessageId(messageId);
        return chatNotificationRepository.saveAndFlush(model);
    }

    public void deleteNotificationFor(Long userId, Long chatId) {
        ChatNotificationModel chatNotificationModel = new ChatNotificationModel();
        chatNotificationModel.setChatId(chatId);
        chatNotificationModel.setUserId(userId);
        chatNotificationRepository
                .findAll(Example.of(chatNotificationModel))
                .forEach(chatNotificationRepository::delete);
    }

    public Long getNotificationCountForChatAndUser(Long chatId, Long userId) {
        ChatNotificationModel chatNotificationModel = new ChatNotificationModel();
        chatNotificationModel.setChatId(chatId);
        chatNotificationModel.setUserId(userId);
        return chatNotificationRepository.count(Example.of(chatNotificationModel));
    }

    public List<ChatNotificationModel> getNotifications(Long userId, Long chatId) {
        ChatNotificationModel chatNotificationModel = new ChatNotificationModel();
        chatNotificationModel.setChatId(chatId);
        chatNotificationModel.setUserId(userId);
        return chatNotificationRepository.findAll(Example.of(chatNotificationModel));
    }
}
