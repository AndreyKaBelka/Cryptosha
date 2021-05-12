package com.messenger.cryptosha.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ChatMessageDTO {
    private Long id;
    private int senderId;
    private Long chatId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;

    public ChatMessageDTO() {
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
