package com.messenger.cryptosha;

import com.messenger.cryptosha.dto.ChatDTO;
import com.messenger.cryptosha.dto.UserDTO;
import com.messenger.cryptosha.model.ChatModel;
import com.messenger.cryptosha.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public class ChatTransformer {

    public UserDTO mapToDTO(UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userModel.getUsername());
        userDTO.setChats(userModel.getChats());
        userDTO.setId(userModel.getUserId());
        return userDTO;
    }

    public UserModel mapToModel(UserDTO userDTO) {
        UserModel userModel = new UserModel();
        userModel.setUsername(userDTO.getUsername());
        userModel.setUserId(userDTO.getId());
        userModel.setChats(userDTO.getChats());
        return userModel;
    }


    public ChatDTO mapToDTO(ChatModel chatModel) {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setChatName(chatModel.getChatName());
        chatDTO.setUsersInChat(chatModel.getUsers());
        chatDTO.setPublicKey(chatModel.getPublicKey());
        chatDTO.setPrivateKey(chatModel.getPrivateKey());
        chatDTO.setId(chatModel.getChatId());
        return chatDTO;
    }
}
