package com.example.entity;

import com.example.task.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@NamedQuery(name = User.FIND_ALLUsers, query = "SELECT u FROM User u")
public class User {
    
    public static final String FIND_ALLUsers = "demo.jsf.User.ALL";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String username;
    
    @NotNull
    @Size(min = 1, max = 100)
    private String password;
    
    // カスタムコンストラクタ
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // toString()メソッドをオーバーライド
    @Override
    public String toString() {
        return String.format("User(ID: %d, ユーザー名: %s)", id, username);
    }
} 