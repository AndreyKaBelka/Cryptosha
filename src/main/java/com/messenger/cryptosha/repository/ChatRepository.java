package com.messenger.cryptosha.repository;

import com.messenger.cryptosha.model.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatModel, Long> {
    @Query(value = "SELECT * FROM chat WHERE chat.chat_id IN (SELECT chat_id FROM user_chats WHERE user_chats.user_id = ?1)",
            nativeQuery = true)
    List<ChatModel> getAllChatsForUser(Long userId);
}
