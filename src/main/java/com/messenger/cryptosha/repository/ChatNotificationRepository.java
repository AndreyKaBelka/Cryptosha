package com.messenger.cryptosha.repository;

import com.messenger.cryptosha.model.ChatNotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNotificationRepository extends JpaRepository<ChatNotificationModel, Long> {
}
