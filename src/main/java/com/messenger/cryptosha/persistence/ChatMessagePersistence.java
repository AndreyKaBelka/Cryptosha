package com.messenger.cryptosha.persistence;

import com.messenger.cryptosha.model.ChatMessageModel;
import com.messenger.cryptosha.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessagePersistence {
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatMessagePersistence(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessageModel saveChatMessage(ChatMessageModel chatMessageModel) {
        return chatMessageRepository.save(chatMessageModel);
    }

    public List<ChatMessageModel> getMessagesForChat(Long chatId) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setChatId(chatId);
        return chatMessageRepository.findAll(Example.of(chatMessageModel));
    }

    public ChatMessageModel getById(Long messageId) {
        return chatMessageRepository.getOne(Math.toIntExact(messageId));
    }
}
