package com.messenger.cryptosha.dto;

import com.messenger.cryptosha.MessageStatus;
import lombok.Data;

@Data
public class ChatNotificationDTO {
    private Long id;
    private Long chatId;
    private MessageStatus messageStatus;
    private Long userId;
    private Long messageId;

}
