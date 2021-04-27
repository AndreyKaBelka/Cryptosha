package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.model.ChatMessageModel;
import com.messenger.cryptosha.persistence.ChatMessagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageProcessingServiceImpl implements MessageProcessingService {
    private final ChatMessagePersistence chatMessagePersistence;
    private final ChatNotificationService notificationService;

    @Autowired
    public MessageProcessingServiceImpl(ChatMessagePersistence chatMessagePersistence, ChatNotificationService notificationService) {
        this.chatMessagePersistence = chatMessagePersistence;
        this.notificationService = notificationService;
    }

    @Override
    public Long saveChatMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessageModel chatMessageModel = mapToModel(chatMessageDTO);
        return chatMessagePersistence.saveChatMessage(chatMessageModel).getId();
    }

    @Override
    public List<ChatMessageDTO> getAllMessagesByChat(Long chatId) {
        return chatMessagePersistence
                .getMessagesForChat(chatId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageDTO> getUnreadMessagesForUserAndChat(Long userId, Long chatId) {
        List<ChatNotificationDTO> notifications = notificationService.getNotificationsForUserAndChat(userId, chatId);
        List<ChatMessageDTO> messages = new ArrayList<>();
        for (ChatNotificationDTO notification: notifications) {
            messages.add(mapToDTO(chatMessagePersistence.getById(notification.getMessageId())));
        }
        return messages;
    }

    private ChatMessageModel mapToModel(ChatMessageDTO chatMessageDTO) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setChatId(chatMessageDTO.getChatId());
        chatMessageModel.setSenderId(chatMessageDTO.getSenderId());
        chatMessageModel.setTimestamp(chatMessageDTO.getTimestamp());
        chatMessageModel.setContent(chatMessageDTO.getContent());
        chatMessageModel.setId(chatMessageDTO.getId());
        return chatMessageModel;
    }

    private ChatMessageDTO mapToDTO(ChatMessageModel chatMessageModel) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setChatId(chatMessageModel.getChatId());
        chatMessageDTO.setSenderId(chatMessageModel.getSenderId());
        chatMessageDTO.setTimestamp(chatMessageModel.getTimestamp());
        chatMessageDTO.setContent(chatMessageModel.getContent());
        chatMessageDTO.setId(chatMessageModel.getId());
        return chatMessageDTO;
    }


}
