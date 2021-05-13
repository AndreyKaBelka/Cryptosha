package com.messenger.cryptosha.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.service.ChatNotificationService;
import com.messenger.cryptosha.service.ChatService;
import com.messenger.cryptosha.service.MessageProcessingService;
import javassist.NotFoundException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebMvcTest(MessageProcessingResource.class)
@MockBean({SimpMessagingTemplate.class, ChatNotificationService.class})
public class MessageProcessingResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageProcessingService messageProcessingService;

    @MockBean
    private ChatService chatService;

    private ObjectMapper objectMapper;
    private ChatMessageDTO[] dtos;
    private static final Long CHAT_ID = 123L;
    private static final Integer USER_ID = 456;

    @Before
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