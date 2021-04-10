package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.OperationsException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatResource {
    private final ChatService chatService;

    @Autowired
    public ChatResource(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> createChat(@RequestParam String chatName) throws OperationsException {
        Long chatId = chatService.createChat(chatName);
        return ResponseEntity.ok(Collections.singletonMap("chatId", chatId));
    }
}
