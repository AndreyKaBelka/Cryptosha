package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProcessingResource {
    private final MessageProcessingService messageProcessingService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatNotificationService chatNotificationService;
    private final ChatService chatService;

    @Autowired
    public MessageProcessingResource(MessageProcessingService messageProcessingService,
                                     SimpMessagingTemplate simpMessagingTemplate,
                                     ChatNotificationService chatNotificationService, ChatService chatService) {
        this.messageProcessingService = messageProcessingService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatNotificationService = chatNotificationService;
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    public void messageHandling(@Payload ChatMessageDTO chatMessageDTO) {
        messageProcessingService.saveChatMessage(chatMessageDTO);
        for (Long userId: chatService.getChatUserIds(chatMessageDTO.getChatId())) {
            chatNotificationService.addNotification(userId, chatMessageDTO.getChatId());
        }
        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDTO.getChatId()), "/queue/messages", "Notification"
        );
    }
}
