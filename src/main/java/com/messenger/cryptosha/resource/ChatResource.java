package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.service.ChatService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.OperationsException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public ResponseEntity<Map<String, Object>> connectToChat(@RequestParam Long chatId, @CookieValue(value = "userId", defaultValue = "-1") Long userId) throws NotFoundException {
        ChatDTO chatDTO = chatService.addUserToChat(chatId, userId);
        Map<String, Object> json = new HashMap<>();
        json.put("chatTitle", chatDTO.getChatName());
        json.put("chatPublicKey", chatDTO.getPublicKey().toString());
        return ResponseEntity.ok(json);
    }

    @GetMapping
    public ResponseEntity<ChatDTO> getChatByIdForUser(@RequestParam Long chatId, @CookieValue(value = "userId", defaultValue = "-1") Long userId) throws NotFoundException {
        ChatDTO chatDTO = chatService.getChatById(chatId);
        if (chatService.isUserConnected(chatId, userId)) {
            return ResponseEntity.ok(chatDTO);
        } else {
            return ResponseEntity.badRequest().body(new ChatDTO());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Set<ChatDTO>> getAllChatsForUser(@CookieValue(value = "userId", defaultValue = "-1") Long userId) {
        Set<ChatDTO> chats = chatService.getAllChatsForUser(userId);
        return ResponseEntity.ok(chats);
    }
}
