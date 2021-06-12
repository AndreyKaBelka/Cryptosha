package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.NotFoundException;
import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.service.ChatNotificationService;
import com.messenger.cryptosha.service.ChatService;
import com.messenger.cryptosha.service.MessageProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CookieValue;
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
        chatMessageDTO.setId(messageId);
        for (Long userId : chatService.getChatUserIds(chatMessageDTO.getChatId())) {
            if (userId != chatMessageDTO.getSenderId()) {
                chatNotificationService.addNotification(userId, chatMessageDTO.getChatId(), messageId);
                simpMessagingTemplate.convertAndSendToUser(
                        String.valueOf(userId), "/queue/messages", chatMessageDTO
                );
            }
        }
    }

    @GetMapping("/getMessages")
    public ResponseEntity<?> getMessages(@RequestParam Long chatId, @CookieValue(value = "userId", defaultValue = "-1") Long userId) throws NotFoundException {
        if (!chatService.isUserConnected(chatId, userId)) {
            return ResponseEntity.badRequest().body("User not connected to this chat!");
        }
        List<ChatMessageDTO> messages = messageProcessingService.getAllMessagesByChat(chatId);
        chatNotificationService.deleteNotification(userId, chatId);
        return ResponseEntity.ok(messages);
    }
}
