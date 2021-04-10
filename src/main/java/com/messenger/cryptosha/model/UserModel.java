package com.messenger.cryptosha.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    private Long userId;

    @ManyToMany
    @JoinTable(
            name = "chaters",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "chatId")
    )
    private Set<ChatModel> userChats;
}
