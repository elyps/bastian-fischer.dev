package com.sup1x.api.repository;

import com.sup1x.api.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByUsername(String username);

/*
    Iterable<Notification> findAllByUsernameAndMessage(String username, String message);

    Iterable<Notification> findAllByUsernameAndMessageAndCreatedAt(String username, String message, String createdAt);

    Iterable<Notification> findAllByUsernameAndMessageAndCreatedAtAndId(String username, String message, String createdAt, String id);

    void deleteAllByUsernameAndMessage(String username, String message);

    void deleteAllByUsernameAndMessageAndCreatedAt(String username, String message, String createdAt);

    void deleteAllByUsernameAndMessageAndCreatedAtAndId(String username, String message, String createdAt, String id);
*/

    void deleteAllByUsername(String username);

    void deleteAllByUsernameAndId(String username, Long id);
}
