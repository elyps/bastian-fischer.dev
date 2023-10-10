package com.sup1x.api.config;

import io.jsonwebtoken.lang.Arrays;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PropertySource(value = "encrypted.properties") // load the encryption key from the properties file and use it to encrypt and decrypt data.
@Configuration
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {

        List<String> resultArray = new ArrayList<>();
        String filePath = "src/main/resources/dev.env";

        String encryptionPassword = "ENC(t5CrgvlJCiWwmLIOUNRLmBcswCGee6O1)";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(encryptionPassword);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder updatedContent = new StringBuilder();
            String line;
            Pattern pattern = Pattern.compile("DEC\\((.*?)\\)");

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String value = matcher.group(1);
                    resultArray.add(value);
                }
                    if (!resultArray.isEmpty()) {
                        for (String value : resultArray) {
                            String encryptedValue = encryptor.encrypt(value);
                            line = line.replace("DEC(" + value + ")", "ENC(" + encryptedValue + ")");
                        }
                    }

                updatedContent.append(line).append(System.lineSeparator());
            }

            // Write the updated content back to the .env file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                bw.write(updatedContent.toString());
                System.out.println("Encryption update ... SUCCESS!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nEncrypting ... FAILURE!");
        }

        return encryptor;
    }
}
