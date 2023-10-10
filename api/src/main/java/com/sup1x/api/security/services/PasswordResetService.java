package com.sup1x.api.security.services;

import com.sup1x.api.model.User;
import com.sup1x.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.mail.MessagingException;

import org.springframework.context.annotation.Bean;
import jakarta.mail.internet.MimeMessage;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public PasswordResetService(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public String initiatePasswordReset(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Generate a unique token for password reset
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);
            userRepository.save(user);

            // Send the password reset link via email
            sendPasswordResetEmail(user.getEmail(), resetToken);
            System.out.println( "Password-Reset-Email send successfully." );
//            return "Password-Reset-Email send successfully.";
        } else {
            // Handle the case when the user does not exist or email is invalid
            System.out.println( "User does not exists or email is invalid" );
//            return "User does not exists or email is invalid";
        }
        return email;
    }

    public String resetPassword(String resetToken, String newPassword) {
        User user = userRepository.findByResetToken(resetToken);
        if (user != null) {
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            user.setResetToken(null);
            userRepository.save(user);
            System.out.println( "Token is valid." );
//            return "Token is invalid or expired";
        } else {
            // Handle the case when the reset token is invalid or expired
            System.out.println( "Token is invalid or expired" );
//            return "Token is invalid or expired";
        }
        return resetToken;
    }

    private void sendPasswordResetEmail(String email, String resetToken) throws MessagingException {
        // Create and send the password reset email
        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setSubject("Password Reset");
        String resetLink = "https://bastian-fischer.dev/reset?token=" + resetToken;
        String emailContent = "Hello,\n\nTo reset your password, please click on the link below:\n" + resetLink;
        helper.setText(emailContent, false);
        mailSender.send(message);
    }
}

/*public void sendPasswordResetEmail(String email, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);
            helper.setSubject("Password Reset");
            String resetLink = "https://your-app-url.com/reset?token=" + resetToken;
            String emailContent = "Hello,\n\nTo reset your password, please click on the link below:\n" + resetLink;
            helper.setText(emailContent, false);
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception if email sending fails
        }
    }*/
