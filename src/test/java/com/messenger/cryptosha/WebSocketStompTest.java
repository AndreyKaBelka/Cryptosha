package com.messenger.cryptosha;

import ch.qos.logback.core.util.TimeUtil;
import com.messenger.cryptosha.dto.ChatMessageDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketStompTest {
    @Value("${local.server.port}")
    private int port;
    private final String URL_SUBSCRIBE = "/user/%d/queue/messages";
    private final Long userId = 1L;
    private ChatMessageDTO testMessage;

    private CompletableFuture<ChatMessageDTO> completableFuture;
    private StompSession stompSession;

    class MessageStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ChatMessageDTO.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.completeAsync(() -> (ChatMessageDTO) o);
        }
    }

    @Before
    public void setUp() throws Exception {
        testMessage = new ChatMessageDTO();
        testMessage.setId(1L);
        testMessage.setChatId(1L);
        testMessage.setContent("Test Content Man!");
        testMessage.setTimestamp(new Date());
        testMessage.setSenderId(2);

        completableFuture = new CompletableFuture<>();
        String URL = String.format("http://localhost:%d/ws", port);

        WebSocketStompClient webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompSession = webSocketStompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);

        stompSession.subscribe(String.format(URL_SUBSCRIBE, userId), new MessageStompFrameHandler());
    }

    @Test
    public void testMessageGetting() throws ExecutionException, InterruptedException, TimeoutException {
        stompSession.send(String.format(URL_SUBSCRIBE, userId), testMessage);
        ChatMessageDTO chatMessageDTO = completableFuture.get(5, TimeUnit.SECONDS);

        Assertions.assertEquals(testMessage, chatMessageDTO);
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }
}
