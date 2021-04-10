package com.messenger.cryptosha.service;

import javax.management.OperationsException;

public interface ChatService {
    Long createChat(String chatName) throws OperationsException;
}
