package com.messenger.cryptosha;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.model.ChatModel;
import com.messenger.cryptosha.model.UserModel;
import com.messenger.cryptosha.persistence.ChatPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatTransformer {
    private final ChatPersistence chatPersistence;

    @Autowired
    public ChatTransformer(ChatPersistence chatPersistence) {
        this.chatPersistence = chatPersistence;
    }

    public UserDTO mapToDTO(UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userModel.getUsername());
        Set<Long> chatsId = userModel
                .getChats()
                .stream()
                .map(ChatModel::getChatId)
                .collect(Collectors.toSet());
        userDTO.setChats(chatsId);
        userDTO.setId(userModel.getUserId());
        return userDTO;
    }

    public UserModel mapToModel(UserDTO userDTO) {
        UserModel userModel = new UserModel();
        userModel.setUsername(userDTO.getUsername());
        userModel.setUserId(userDTO.getId());
        Set<ChatModel> chatModels = userDTO
                .getChats()
                .stream()
                .map(chatPersistence::getChatById)
                .collect(Collectors.toSet());
        userModel.setChats(chatModels);
        return userModel;
    }


    public ChatDTO mapToDTO(ChatModel chatModel) {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setChatName(chatModel.getChatName());
        Set<Long> usersInChat = chatModel
                .getUsers().stream()
                .map(UserModel::getUserId)
                .collect(Collectors.toSet());
        chatDTO.setUsersIdInChat(usersInChat);
        chatDTO.setPublicKey(chatModel.getPublicKey());
        chatDTO.setPrivateKey(chatModel.getPrivateKey());
        chatDTO.setId(chatModel.getChatId());
        return chatDTO;
    }
}
