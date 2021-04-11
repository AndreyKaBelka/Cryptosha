package com.messenger.cryptosha.model;

import com.messenger.cryptosha.MessageStatus;

import javax.persistence.*;

@Entity
@Table(name = "chat_notification")
public class ChatNotificationModel {
    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
