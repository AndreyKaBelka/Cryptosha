package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.service.ChatNotificationService;
import com.messenger.cryptosha.service.ChatService;
import com.messenger.cryptosha.service.MessageProcessingService;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@TestComponent
public class MessageProcessingResourceTest {
    @MockBean
    private MessageProcessingService messageProcessingService;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;

    @MockBean
    private ChatNotificationService chatNotificationService;

    @MockBean
    private ChatService chatService;

    private MessageProcessingResource messageProcessingResource;

    private ChatMessageDTO[] dtos;

    public MessageProcessingResourceTest() {
    }

    @Before
    public void setUp() {
        dtos = new ChatMessageDTO[2];
        for (int i = 0; i < dtos.length; i++) {
            dtos[i] = new ChatMessageDTO();
            dtos[i].setChatId(123L);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.set(Calendar.DATE, i);
            dtos[i].setTimestamp(calendar.getTime());
            dtos[i].setSenderId(456);
            dtos[i].setContent("TEST CONTENT!");
        }
        given(messageProcessingService.getAllMessagesByChat(dtos[0].getChatId())).willReturn(Lists.list(dtos));
        messageProcessingResource = new MessageProcessingResource(messageProcessingService,
                simpMessagingTemplate,
                chatNotificationService,
                chatService);
    }

    @Test
    public void getMessageTest() {
        List<ChatMessageDTO> messages = messageProcessingResource.getMessage(dtos[0].getChatId(), 123L);
        Assert.assertArrayEquals(dtos, messages.toArray());
    }

    @Test
    public void getNullMessagesTest() {
        List<ChatMessageDTO> messages = messageProcessingResource.getMessage(87L, 123L);
        Assert.assertArrayEquals(new ChatMessageDTO[0], messages.toArray());
    }
}