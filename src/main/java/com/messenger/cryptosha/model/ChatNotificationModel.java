package com.messenger.cryptosha.model;

import com.messenger.cryptosha.MessageStatus;

import javax.persistence.*;

@Entity
@Table(name = "chatNotification")
public class ChatNotificationModel {
    @Id
    @GeneratedValue
    private Long id;


    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;
}
