package com.sup1x.api.controller;

import com.sup1x.api.model.Notification;
import com.sup1x.api.repository.NotificationRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Tag(name = "Notifications V1", description = "Notification V1 Management API")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {

        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            notification.setUsername(username);
//        Notification savedNotification =
            notificationRepository.save(notification);

//        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.schedule(() -> {
//            // Logik zum Ausblenden der Benachrichtigung
//            notificationRepository.delete(savedNotification);
//            System.out.println("Notification hidden after 5 seconds");
//        }, 5, TimeUnit.SECONDS);

            return ResponseEntity.ok("Notification sent successfully");
        }
        return ResponseEntity.ok("User not authenticated");
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationRepository.findAll());
    }

    @GetMapping("/all/{username}")
    public ResponseEntity<Iterable<Notification>> getAllNotificationsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(notificationRepository.findAllByUsername(username));
    }

/*    @GetMapping("/all/{username}/{message}")
    public ResponseEntity<Iterable<Notification>> getAllNotificationsByUsernameAndMessage(@PathVariable String username, @PathVariable String message) {
        return ResponseEntity.ok(notificationRepository.findAllByUsernameAndMessage(username, message));
    }

    @GetMapping("/all/{username}/{message}/{createdAt}")
    public ResponseEntity<Iterable<Notification>> getAllNotificationsByUsernameAndMessageAndCreatedAt(@PathVariable String username, @PathVariable String message, @PathVariable String createdAt) {
        return ResponseEntity.ok(notificationRepository.findAllByUsernameAndMessageAndCreatedAt(username, message, createdAt));
    }

    @GetMapping("/all/{username}/{message}/{createdAt}/{id}")
    public ResponseEntity<Iterable<Notification>> getAllNotificationsByUsernameAndMessageAndCreatedAtAndId(@PathVariable String username, @PathVariable String message, @PathVariable String createdAt, @PathVariable String id) {
        return ResponseEntity.ok(notificationRepository.findAllByUsernameAndMessageAndCreatedAtAndId(username, message, createdAt, id));
    }*/

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotificationById(@PathVariable String id) {
        notificationRepository.deleteById(Long.valueOf(id));
        return ResponseEntity.ok("Notification deleted successfully");
    }

/*    @DeleteMapping("/delete/{username}/{message}")
    public ResponseEntity<String> deleteNotificationByUsernameAndMessage(@PathVariable String username, @PathVariable String message) {
        notificationRepository.deleteAllByUsernameAndMessage(username, message);
        return ResponseEntity.ok("Notification deleted successfully");
    }*/

    @DeleteMapping("/delete/{username}/{id}")
    public ResponseEntity<String> deleteNotificationByUsernameAndId(@PathVariable String username, @PathVariable String id) {
        notificationRepository.deleteAllByUsernameAndId(username, Long.valueOf(id));
        return ResponseEntity.ok("Notification deleted successfully");
    }

/*    @DeleteMapping("/delete/{username}/{message}/{createdAt}")
    public ResponseEntity<String> deleteNotificationByUsernameAndMessageAndCreatedAt(@PathVariable String username, @PathVariable String message, @PathVariable String createdAt) {
        notificationRepository.deleteAllByUsernameAndMessageAndCreatedAt(username, message, createdAt);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @DeleteMapping("/delete/{username}/{message}/{createdAt}/{id}")
    public ResponseEntity<String> deleteNotificationByUsernameAndMessageAndCreatedAtAndId(@PathVariable String username, @PathVariable String message, @PathVariable String createdAt, @PathVariable String id) {
        notificationRepository.deleteAllByUsernameAndMessageAndCreatedAtAndId(username, message, createdAt, id);
        return ResponseEntity.ok("Notification deleted successfully");
    }*/

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllNotifications() {
        notificationRepository.deleteAll();
        return ResponseEntity.ok("All notifications deleted successfully");
    }

    @DeleteMapping("/delete/all/{username}")
    public ResponseEntity<String> deleteAllNotificationsByUsername(@PathVariable String username) {
        notificationRepository.deleteAllByUsername(username);
        return ResponseEntity.ok("All notifications deleted successfully");
    }

}
