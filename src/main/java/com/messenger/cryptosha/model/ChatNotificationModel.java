package com.messenger.cryptosha.model;

import com.messenger.cryptosha.MessageStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "chat_notification")
@Data
public class ChatNotificationModel {
    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private Long userId;
    private Long messageId;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

}
