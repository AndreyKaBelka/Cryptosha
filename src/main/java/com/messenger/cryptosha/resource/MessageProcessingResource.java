package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.dto.ChatNotificationDTO;
import com.messenger.cryptosha.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageProcessingResource {
    private final MessageProcessingService messageProcessingService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatNotificationService chatNotificationService;
    private final ChatService chatService;

    @Autowired
    public MessageProcessingResource(MessageProcessingService messageProcessingService,
                                     SimpMessagingTemplate simpMessagingTemplate,
                                     ChatNotificationService chatNotificationService,
                                     ChatService chatService) {
        this.messageProcessingService = messageProcessingService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatNotificationService = chatNotificationService;
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    public void messageHandling(@Payload ChatMessageDTO chatMessageDTO) {
        Long messageId = messageProcessingService.saveChatMessage(chatMessageDTO);
        for (Long userId: chatService.getChatUserIds(chatMessageDTO.getChatId())) {
            chatNotificationService.addNotification(userId, chatMessageDTO.getChatId(), messageId);
            simpMessagingTemplate.convertAndSendToUser(
                    String.valueOf(userId), "/queue/messages", "Notification"
            );
        }
    }

    @GetMapping("/getMessages")
    public List<ChatMessageDTO> getMessage(@RequestParam Long chatId, @RequestParam Long userId) {
        List<ChatMessageDTO> messages = messageProcessingService.getAllMessagesByChat(chatId);
        chatNotificationService.deleteNotification(userId, chatId);
        return messages;
    }
}
