package com.messenger.cryptosha.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.service.ChatService;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebMvcTest(ChatResource.class)
class ChatResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;
    private ObjectMapper objectMapper;

    private ChatDTO[] chats;
    private UserDTO user;
    private static final String CHAT_NAME1 = "TESTNAME";
    private static final String CHAT_NAME2 = "TESTNAME2";
    private static final String CHAT_PUB = "{0;0}";
    private static final Long CHAT_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "TESTUSER";

    @BeforeEach
    void setUp() throws NotFoundException {
        objectMapper = new ObjectMapper();
        chats = new ChatDTO[2];
        user = new UserDTO();
        user.setId(USER_ID);
        user.setUsername(USER_NAME);
        user.setChats(Collections.singleton(CHAT_ID));

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

        given(chatService.getAllChatsForUser(USER_ID)).willReturn(Arrays.stream(chats).collect(Collectors.toSet()));
        given(chatService.getChatById(CHAT_ID)).willReturn(chats[0]);
        given(chatService.getChatById(2L)).willReturn(chats[1]);
        given(chatService.isUserConnected(CHAT_ID, USER_ID)).willReturn(true);
    }

    @Test
    void getChatByIdForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                .param("chatId", String.valueOf(CHAT_ID))
                .cookie(new Cookie("userId", USER_ID.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(chats[0])));
    }

    @Test
    void getChatForNotConnectedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat")
                .param("chatId", String.valueOf(2L))
                .cookie(new Cookie("userId", USER_ID.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllChatsForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/all")
                .cookie(new Cookie("userId", USER_ID.toString()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(chats))));
    }

    @Test
    void getAllChatsForWrongUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/chat/all")
                .cookie(new Cookie("userId", "2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}