package com.messenger.cryptosha.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat")
@Data
public class ChatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;
    private String privateKey;
    private String publicKey;
    private String chatName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_chats",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserModel> users = new HashSet<>();

    public void removeUser(UserModel user) {
        this.users.remove(user);
        user.getChats().remove(this);
    }

    public void addUser(UserModel user) {
        this.users.add(user);
        user.getChats().add(this);
    }
}
