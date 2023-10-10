package com.sup1x.api.service;

import com.sup1x.api.model.Notification;
import com.sup1x.api.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(
            SimpMessagingTemplate messagingTemplate,
            NotificationRepository notificationRepository) {
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(String message) {

        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();


            // Send WebSocket notification
            messagingTemplate.convertAndSend("/topic/notifications", message);

            // Save notification in the database
            Notification notification = new Notification();
            notification.setMessage(message);
//            notification.setCreatedAt(LocalDateTime.now());
            notification.setUsername(username);
            notificationRepository.save(notification);
        }
    }
}
