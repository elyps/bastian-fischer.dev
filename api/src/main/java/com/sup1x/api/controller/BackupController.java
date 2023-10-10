package com.sup1x.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.DeleteMapping;

@CrossOrigin(origins = "http://localhost:8083", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Database V1", description = "Database V1 Management API")
@RestController
@RequestMapping("/api/v1")
public class BackupController {

    @GetMapping("/database/h2/backup")
    public String createBackup() throws IOException {

        // Aktuelles Datum und Uhrzeit im gewünschten Format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateTime = dateFormat.format(new Date());

        // Pfad zum Speichern des Backups mit aktuellem Datum und Uhrzeit
        String backupPath = "./backup/dbhp_backup_" + currentDateTime + ".zip";

        // H2-Datenbankpfad
        String h2DatabasePath = "./data/dbhp.mv.db";

        // Überprüfe, ob die Ordner im Backup-Pfad existieren, andernfalls erstelle sie
        Path backupFolderPath = Paths.get(backupPath).getParent();

        if (!Files.exists(backupFolderPath)) {
            Files.createDirectories(backupFolderPath);
        }

        // Erstelle ein Backup der H2-Datenbank
        if ("".equals(h2DatabasePath) && h2DatabasePath.isEmpty()) {
            return "H2-Datenbankpfad ist leer. Backup konnte nicht erstellt werden.";
        } else {
            Files.copy(Paths.get(h2DatabasePath), Paths.get(backupPath));
            return "Backup erfolgreich erstellt: " + backupPath;
        }
    }

    @DeleteMapping("/database/h2/backups/delete/all")
    public String deleteBackup() throws IOException {

        createBackup();

        // Lösche alle Backups
        Path backupFolder = Paths.get("./backup/");

        Files.walk(backupFolder)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".zip"))
/*                 .filter(path -> {
                    try {
                        return Files.getLastModifiedTime(path).toMillis() < System.currentTimeMillis()
                                - 30 * 24 * 60 * 60 * 1000;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                }) */
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return "Alle Backups wurden gelöscht.";

    }

}
