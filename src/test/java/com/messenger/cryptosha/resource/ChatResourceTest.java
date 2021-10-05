package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.exceptions.NotFoundException;
import com.messenger.cryptosha.service.ChatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChatResourceTest {
    private ChatResource chatResource;

    private ChatDTO[] chats;
    private static final String CHAT_NAME1 = "TESTNAME";
    private static final String CHAT_NAME2 = "TESTNAME2";
    private static final String CHAT_PUB = "{0;0}";
    private static final Long CHAT_ID = 1L;
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() throws NotFoundException {
        chats = new ChatDTO[2];

        ChatDTO chat = new ChatDTO();
        chat.setId(CHAT_ID);
        chat.setChatName(CHAT_NAME1);
        chat.setPublicKey(CHAT_PUB);
        chat.setPrivateKey("{1;1}");
        chat.setUsersIdInChat(Collections.singleton(USER_ID));
        chats[0] = chat;

        chat = new ChatDTO();
        chat.setId(CHAT_ID + 1L);
        chat.setChatName(CHAT_NAME2);
        chat.setPublicKey(CHAT_PUB.replace("0", "1"));
        chat.setPrivateKey("{1;1}");
        chat.setUsersIdInChat(Collections.singleton(USER_ID));
        chats[1] = chat;

        ChatService chatService = mock(ChatService.class);
        given(chatService.getAllChatsForUser(USER_ID)).willReturn(Collections.singleton(chats[0]));
        given(chatService.getChatById(CHAT_ID)).willReturn(chats[0]);
        given(chatService.getChatById(2L)).willReturn(chats[1]);
        given(chatService.isUserConnected(CHAT_ID, USER_ID)).willReturn(true);

        chatResource = new ChatResource(chatService);
    }

    @Test
    void getChatByIdForUser() {
        ResponseEntity<ChatDTO> entity = chatResource.getChatByIdForUser(CHAT_ID, USER_ID);
        Assertions.assertEquals(200, entity.getStatusCode().value());
        Assertions.assertEquals(chats[0], entity.getBody());
    }

    @Test
    void getChatForNotConnectedUser() {
        ResponseEntity<ChatDTO> entity = chatResource.getChatByIdForUser(2L, USER_ID);
        Assertions.assertTrue(entity.getStatusCode().isError());
    }

    @Test
    void getAllChatsForUser() {
        ResponseEntity<?> response = chatResource.getAllChatsForUser(USER_ID);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(Collections.singleton(chats[0]), response.getBody());
    }

    @Test
    void getAllChatsForWrongUser() {
        ResponseEntity<Set<ChatDTO>> response = chatResource.getAllChatsForUser(2L);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }
}