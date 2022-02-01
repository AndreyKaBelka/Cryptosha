package com.messenger.cryptosha.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.cryptosha.configuration.SpringSecurityTestConfiguration;
import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.exceptions.NotFoundException;
import com.messenger.cryptosha.service.ChatService;
import com.messenger.cryptosha.service.MessageProcessingService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(
    locations = "classpath:application-test.properties"
)
@SpringBootTest(classes = SpringSecurityTestConfiguration.class)
@AutoConfigureMockMvc
public class MessageProcessingResourceTest {
    private static final Long CHAT_ID = 123L;
    private static final Integer USER_ID = 456;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageProcessingService messageProcessingService;
    @MockBean
    private ChatService chatService;
    private ObjectMapper objectMapper;
    private ChatMessageDTO[] dtos;

    @BeforeEach
    public void setUp() throws NotFoundException {
        objectMapper = new ObjectMapper();
        dtos = new ChatMessageDTO[2];
        for (int i = 0; i < dtos.length; i++) {
            dtos[i] = new ChatMessageDTO();
            dtos[i].setId(i + 1L);
            dtos[i].setChatId(CHAT_ID);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.set(Calendar.DATE, i);
            dtos[i].setTimestamp(calendar.getTime());
            dtos[i].setSenderId(USER_ID);
            dtos[i].setContent("TEST CONTENT!");
        }
        given(messageProcessingService.getAllMessagesByChat(CHAT_ID)).willReturn(Lists.list(dtos));
        given(chatService.isUserConnected(CHAT_ID, USER_ID.longValue())).willReturn(true);
    }

    @Test
    public void getMessageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getMessages")
                .param("chatId", String.valueOf(CHAT_ID))
                .cookie(new Cookie("userId", String.valueOf(USER_ID))))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(Lists.list(dtos))));
    }

    @Test
    public void getNullMessagesTestForWrongChatId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getMessages")
                .param("chatId", "3")
                .cookie(new Cookie("userId", String.valueOf(USER_ID))))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getNullMessagesTestForWrongUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getMessages")
                .param("chatId", String.valueOf(CHAT_ID))
                .cookie(new Cookie("userId", "2")))
            .andExpect(status().isBadRequest());
    }
}