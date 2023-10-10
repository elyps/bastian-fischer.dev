package com.sup1x.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import com.sup1x.api.model.GitHubRepo;
import com.sup1x.api.repository.GitHubRepoRepository;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHUser;

import com.sup1x.api.config.RestTemplateConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

@Service
public class GitHubService {

    // logger
    private static final Logger logger = LoggerFactory.getLogger(GitHubService.class);

    private final GitHubRepoRepository gitHubRepoRepository;

    // @Autowired
    // private final RestTemplate restTemplate;

    @Autowired
    public GitHubService(GitHubRepoRepository gitHubRepoRepository) {
        this.gitHubRepoRepository = gitHubRepoRepository;
    }

    /*
     * public void saveGitHubRepoData(String githubUsername, String githubRepoName)
     * {
     * try {
     * // GitHub-Verbindung erstellen (wie im vorherigen Beispiel gezeigt)
     * GitHub github =
     * GitHub.connectUsingOAuth("");
     * GHRepository repository = github.getRepository(githubUsername + "/" +
     * githubRepoName);
     * 
     * // GitHub-Daten in ein GitHubRepo-Objekt mappen
     * GitHubRepo gitHubRepo = new GitHubRepo();
     * gitHubRepo.setName(repository.getName());
     * gitHubRepo.setDescription(repository.getDescription());
     * 
     * // README-Datei abrufen (falls vorhanden)
     * GHContent readmeContent = repository.getFileContent("README.md");
     * if (readmeContent != null) {
     * gitHubRepo.setReadMe(readmeContent.getContent());
     * }
     * 
     * // GitHubRepo-Objekt in der H2-Datenbank speichern
     * gitHubRepoRepository.save(gitHubRepo);
     * 
     * // return "Successfully fetched & saved Repositories from Github. These repos
     * // were found: " + repository.getName() + "
     * " + repository.getDescription() + "
     * // " + readmeContent.getContent();
     * 
     * // Optional: Loggen Sie den Erfolg oder andere Aktionen
     * System.out.println("GitHub-Daten erfolgreich in der Datenbank gespeichert.");
     * logger.info("GitHub-Daten erfolgreich in der Datenbank gespeichert.");
     * } catch (Exception e) {
     * // Behandeln Sie Fehler angemessen, z.B. protokollieren Sie sie oder geben
     * Sie
     * // eine Fehlermeldung aus
     * e.printStackTrace();
     * }
     * }
     */

    public void saveAllReposForUser() {
        try {
            // GitHub-Verbindung erstellen (wie im vorherigen Beispiel gezeigt)
            GitHub github = GitHub.connectUsingOAuth("");

            // Repositories des Benutzers abrufen
            for (GHRepository repository : github.getUser("elyps").listRepositories()) {
                // GitHub-Daten in ein GitHubRepo-Objekt mappen
                GitHubRepo gitHubRepo = new GitHubRepo();
                gitHubRepo.setName(repository.getName());
                gitHubRepo.setDescription(repository.getDescription());

                // GitHubRepo-Objekt in der H2-Datenbank speichern
                gitHubRepoRepository.save(gitHubRepo);
            }

            // Optional: Loggen Sie den Erfolg oder andere Aktionen
            System.out.println("Alle Repositories des Benutzers mit README erfolgreich in der Datenbank gespeichert.");
        } catch (Exception e) {
            // Behandeln Sie Fehler angemessen, z.B. protokollieren Sie sie oder geben Sie
            // eine Fehlermeldung aus
            e.printStackTrace();
        }
    }

}
