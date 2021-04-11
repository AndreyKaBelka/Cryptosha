package com.messenger.cryptosha.service;

import com.messenger.cryptosha.dto.ChatMessageDTO;
import com.messenger.cryptosha.model.ChatMessageModel;
import com.messenger.cryptosha.persistence.ChatMessagePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageProcessingServiceImpl implements MessageProcessingService {
    private final ChatMessagePersistence chatMessagePersistence;

    @Autowired
    public MessageProcessingServiceImpl(ChatMessagePersistence chatMessagePersistence) {
        this.chatMessagePersistence = chatMessagePersistence;
    }

    @Override
    public void saveChatMessage(ChatMessageDTO chatMessageDTO) {
        ChatMessageModel chatMessageModel = mapToModel(chatMessageDTO);
        chatMessagePersistence.saveChatMessage(chatMessageModel);
    }

    @Override
    public List<ChatMessageDTO> getAllMessagesByChat(Long chatId) {
        return chatMessagePersistence
                .getMessagesForChat(chatId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ChatMessageModel mapToModel(ChatMessageDTO chatMessageDTO) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setChatId(chatMessageDTO.getChatId());
        chatMessageModel.setSenderId(chatMessageDTO.getSenderId());
        chatMessageModel.setTimestamp(chatMessageDTO.getTimestamp());
        chatMessageModel.setContent(chatMessageDTO.getContent());
        return chatMessageModel;
    }

    private ChatMessageDTO mapToDTO(ChatMessageModel chatMessageModel) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setChatId(chatMessageModel.getChatId());
        chatMessageDTO.setSenderId(chatMessageModel.getSenderId());
        chatMessageDTO.setTimestamp(chatMessageModel.getTimestamp());
        chatMessageDTO.setContent(chatMessageModel.getContent());
        return chatMessageDTO;
    }


}
