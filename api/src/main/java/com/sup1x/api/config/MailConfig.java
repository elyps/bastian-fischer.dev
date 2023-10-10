package com.sup1x.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    public String HOST;

    @Value("${spring.mail.port}")
    public int PORT;

    @Value("${spring.mail.username}")
    public String USERNAME;

    @Value("${spring.mail.password}")
    public String PASSWORD;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        // Set SMTP properties
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enables STARTTLS
        props.put("mail.smtp.ssl.trust", HOST); // Optional: If needed, specify the host to trust

        return mailSender;
    }
}
