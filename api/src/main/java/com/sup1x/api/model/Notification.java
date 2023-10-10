package com.sup1x.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
//    private String type;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String username;

    public Notification() {
    }

    public Notification(String message, /*String type, */LocalDateTime createdAt, String username) {
        this.message = message;
//        this.type = type;
        this.createdAt = createdAt;
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /*public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }*/
}
