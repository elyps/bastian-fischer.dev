package com.sup1x.api;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sup1x.api.repository.BuildInfoRepository;
import com.sup1x.api.model.BuildInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;


//@EnableConfigurationProperties(LiquibaseProperties.class)
@SpringBootApplication
@EnableEncryptableProperties
@EnableWebSocket
@EnableWebSocketMessageBroker
@EntityScan(basePackages = {"com.sup1x.api.model"})
public class ApiApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    @Autowired
    private BuildInfoRepository buildInfoRepository;

    private static int buildNumber = 0;


    public static void main(String[] args) {
        buildNumber++;

        SpringApplication.run(ApiApplication.class, args);
    }


    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        ApiApplication.jdbcTemplate = jdbcTemplate;
    }

    private static void updateBuildNumber() {
        // Versuche den aktuellen Eintrag in der Datenbank zu aktualisieren
        try {
            jdbcTemplate.update("UPDATE build_info SET buildNumber = buildNumber + 1 WHERE id = 1");
        } catch (EmptyResultDataAccessException e) {
            // Falls der Eintrag nicht existiert, lege einen neuen Eintrag an
            createNewBuildInfo();
        }
    
        // Hole den aktuellen buildNumber-Wert aus der Datenbank
        Integer buildNumber = jdbcTemplate.queryForObject("SELECT buildNumber FROM build_info WHERE id = 1", Integer.class);
        logger.info("Build Number: " + buildNumber);
    }
    
    private static void createNewBuildInfo() {
        // Lege einen neuen Eintrag in der Datenbank an
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("build_info").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("buildNumber", 1);
        Number key = jdbcInsert.executeAndReturnKey(parameters);
    
        // Setze den buildNumber auf den aktuellen Wert
        Integer buildNumber = jdbcTemplate.queryForObject("SELECT buildNumber FROM build_info WHERE id = 1", Integer.class);
        logger.info("Build Number: " + buildNumber);
    }
    
}