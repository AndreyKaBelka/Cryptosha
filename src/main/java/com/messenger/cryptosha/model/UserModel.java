package com.messenger.cryptosha.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class UserModel {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long userId;
    private String username;
    private String passwordHash;

    @ManyToMany(mappedBy = "users")
    private Set<ChatModel> chats = new HashSet<>();

}
