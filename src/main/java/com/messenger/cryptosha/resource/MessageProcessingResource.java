package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.service.MessageProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProcessingResource {
    private final MessageProcessingService messageProcessingService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageProcessingResource(MessageProcessingService messageProcessingService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageProcessingService = messageProcessingService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/sendMessage")
    public void messageHandling(@Payload ChatMessageDTO chatMessageDTO) {
        messageProcessingService.saveChatMessage(chatMessageDTO);
        ChatNotificationDTO chatNotificationDTO = new ChatNotificationDTO();

        simpMessagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessageDTO.getChatId()), "/queue/messages", chatNotificationDTO
        );
    }
}
