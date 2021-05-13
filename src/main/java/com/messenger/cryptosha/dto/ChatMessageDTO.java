package com.messenger.cryptosha.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;

import java.util.Date;

public class ChatMessageDTO implements Cloneable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageDTO that = (ChatMessageDTO) o;
        return senderId == that.senderId && Objects.equal(id, that.id) && Objects.equal(chatId, that.chatId) && Objects.equal(content, that.content) && Objects.equal(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, senderId, chatId, content, timestamp);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChatMessageDTO clone = ((ChatMessageDTO) super.clone());
        clone.setId(this.id);
        clone.setChatId(this.chatId);
        clone.setTimestamp(this.timestamp);
        clone.setContent(this.content);
        clone.setSenderId(this.senderId);
        return clone;
    }

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp.getTime() +
                '}';
    }
}
