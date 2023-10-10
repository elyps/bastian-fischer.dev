package com.sup1x.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.sup1x.api.model.GitInfoProvider;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sup1x.api.service.GitHubService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sup1x.api.repository.GitHubRepoRepository;
import com.sup1x.api.model.GitHubRepo;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Github Provider V1", description = "The Github V1 Management API")
public class GitHubController {

    @Autowired
    private GitHubRepoRepository gitHubRepoRepository;

    @Autowired
    private GitHubService gitHubService;

    public GitHubController(GitHubRepoRepository gitHubRepoRepository, GitHubService gitHubService) {
        this.gitHubRepoRepository = gitHubRepoRepository;
        this.gitHubService = gitHubService;
    }

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

/*     @PostMapping("/github/repository")
    public void saveGitHubRepoData(@RequestParam String githubUsername, @RequestParam String githubRepoName) {
        gitHubService.saveGitHubRepoData(githubUsername, githubRepoName);
    } */

    // get all github repositories from user elyps
/*     @PostMapping("/github/repositories")
    public void saveAllReposForUserWithReadme(@RequestParam String githubUsername) {
        gitHubService.saveAllReposForUserWithReadme(githubUsername);
    } */
    
    @PostMapping("/github/repositories/save")
    public void saveAllReposForUser() {
        deleteAllGitHubRepos();
        gitHubService.saveAllReposForUser();
    }

    @GetMapping("/github/repositories")
    public Iterable<GitHubRepo> getAllGitHubRepos() {
        return gitHubRepoRepository.findAll();
    }
/* 
    @GetMapping("/github/repositories/{id}")
    public Optional<GitHubRepo> getGitHubRepoById(@PathVariable Long id) {
        return gitHubRepoRepository.findById(id);
    }

    @GetMapping("/github/repositories/name/{name}")
    public Optional<GitHubRepo> getGitHubRepoByName(@PathVariable String name) {
        return gitHubRepoRepository.findByName(name);
    } */

    // delete all github repositories from user elyps
    @DeleteMapping("/github/repositories/delete")
    public void deleteAllGitHubRepos() {
        gitHubRepoRepository.deleteAll();
    }

}
