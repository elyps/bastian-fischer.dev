package com.sup1x.api.controller;

import com.sup1x.api.security.services.PasswordResetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@Tag(name = "Auth V1", description = "Auth V1 management API")
@RestController
@RequestMapping("/api/v1/auth/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/initiate")
    public void initiatePasswordReset(@RequestParam String email) throws MessagingException {
        passwordResetService.initiatePasswordReset(email);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestParam String resetToken, @RequestParam String newPassword) {
        passwordResetService.resetPassword(resetToken, newPassword);
    }
}
