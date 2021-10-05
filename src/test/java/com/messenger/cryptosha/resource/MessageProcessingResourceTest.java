package com.messenger.cryptosha.resource;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.exceptions.NotFoundException;
import com.messenger.cryptosha.service.ChatNotificationService;
import com.messenger.cryptosha.service.ChatService;
import com.messenger.cryptosha.service.MessageProcessingService;
import org.junit.jupiter.api.*;
import org.mockito.internal.verification.Times;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.*;

import static org.mockito.Mockito.*;

public class MessageProcessingResourceTest {
    private static MessageProcessingResource messageProcessingResource;
    private static ChatNotificationService chatNotificationService;
    private static SimpMessagingTemplate template;
    private static ChatMessageDTO[] dtos;
    private static final Long CHAT_ID = 123L;
    private static final Long USER_ID = 456L;
    private static final Long USER_ID_2 = 123L;

    @BeforeAll
    static void setUp() throws NotFoundException {
        MessageProcessingService messageProcessingService = mock(MessageProcessingService.class);
        template = mock(SimpMessagingTemplate.class);
        chatNotificationService = mock(ChatNotificationService.class);
        ChatService chatService = mock(ChatService.class);
        dtos = new ChatMessageDTO[2];
        for (int i = 0; i < dtos.length; i++) {
            dtos[i] = new ChatMessageDTO();
            dtos[i].setId(i + 1L);
            dtos[i].setChatId(CHAT_ID);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.set(Calendar.DATE, i);
            dtos[i].setTimestamp(calendar.getTime());
            dtos[i].setSenderId(USER_ID.intValue());
            dtos[i].setContent("TEST CONTENT!");
        }
        when(messageProcessingService.saveChatMessage(any())).thenReturn(dtos[0].getId(), dtos[1].getId());
        when(chatService.getChatUserIds(eq(CHAT_ID))).thenReturn(new Long[]{USER_ID, USER_ID_2});
        when(chatService.isUserConnected(CHAT_ID, USER_ID)).thenReturn(true);
        when(messageProcessingService.getAllMessagesByChat(CHAT_ID)).thenReturn(Arrays.asList(dtos));

        messageProcessingResource = new MessageProcessingResource(
                messageProcessingService,
                template,
                chatNotificationService,
                chatService
        );
    }

    @Test
    public void getMessageTest() {
        ResponseEntity<List<ChatMessageDTO>> messages =
                (ResponseEntity<List<ChatMessageDTO>>) messageProcessingResource.getMessages(CHAT_ID, USER_ID);
        Assertions.assertIterableEquals(Arrays.asList(dtos), messages.getBody());
    }

    @Test
    public void getNullMessagesTestForWrongChatId() {
        ResponseEntity<?> entity = messageProcessingResource.getMessages(2L, USER_ID);
        Assertions.assertTrue(entity.getStatusCode().isError());
    }

    @Test
    public void getNullMessagesTestForWrongUserId() {
        ResponseEntity<?> entity = messageProcessingResource.getMessages(CHAT_ID, 23L);
        Assertions.assertTrue(entity.getStatusCode().isError());
    }

    @RepeatedTest(2)
    public void sendMessages(RepetitionInfo info) {
        messageProcessingResource.messageHandling(dtos[info.getCurrentRepetition() - 1]);
        verify(chatNotificationService, new Times(1)).addNotification(
                eq(USER_ID_2),
                eq(CHAT_ID),
                eq(Long.valueOf(info.getCurrentRepetition()))
        );
    }
}