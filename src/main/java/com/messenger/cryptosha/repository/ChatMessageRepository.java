package com.messenger.cryptosha.repository;

import com.messenger.cryptosha.model.ChatMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageModel, Integer> {
}
