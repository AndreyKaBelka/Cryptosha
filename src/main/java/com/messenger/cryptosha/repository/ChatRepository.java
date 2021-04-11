package com.messenger.cryptosha.repository;

import com.messenger.cryptosha.model.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatModel, Long> {
}
