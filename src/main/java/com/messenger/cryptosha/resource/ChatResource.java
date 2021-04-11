package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.OperationsException;
import java.util.HashMap;
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
        ChatDTO chatDTO = chatService.createChat(chatName);
        Map<String, Object> json = new HashMap<>();
        json.put("chatId", chatDTO.getId());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/connect")
    public ResponseEntity<Map<String, Object>> connectToChat(@RequestParam Long chatId, @RequestParam Long userId) {
        ChatDTO chatDTO = chatService.addUserToChat(chatId, userId);
        Map<String, Object> json = new HashMap<>();
        json.put("chatTitle", chatDTO.getChatName());
        json.put("chatPublicKey", chatDTO.getPublicKey().toString());
        return ResponseEntity.ok(json);
    }
}
