package com.messenger.cryptosha.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;
import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDTO implements Cloneable {
    private Long id;
    private int senderId;
    private Long chatId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;


}
