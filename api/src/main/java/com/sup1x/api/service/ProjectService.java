package com.sup1x.api.service;

import com.sup1x.api.model.Project;
import com.sup1x.api.model.Tag;
import com.sup1x.api.repository.ProjectRepository;
import com.sup1x.api.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
// @EnableCaching
public class ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // @Cacheable("projects")
    // public List<Project> findAll() {
    //     doLongRunningTask();

    //     return projectRepository.findAll();
    // }

    // // @Cacheable("title")
    // public List<Project> findByTitleContaining(String title) {
    //     doLongRunningTask();

    //     return projectRepository.findByTitleContaining(title, Sort.by(Sort.Direction.DESC, "id"));
    // }

    // // @Cacheable("{id}")
    // public Optional<Project> findById(long id) {
    //     doLongRunningTask();

    //     return projectRepository.findById(id);
    // }

    // // @Cacheable("published")
    // public List<Project> findByPublished(boolean published) {
    //     doLongRunningTask();

    //     return projectRepository.findByPublished(published);
    // }

    // // other methods...

    // private void doLongRunningTask() {
    //     try {
    //         Thread.sleep(3000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    // // Methode zum Hinzufügen eines "Likes" zu einem Projekt
    // public void likeProject(Long projectId) {
    //     Project project = projectRepository.findById(projectId).orElse(null);
    //     if (project != null) {
    //         int currentLikes = project.getLikes();
    //         project.setLikes(currentLikes + 1);
    //         projectRepository.save(project);
    //     }
    // }

    // // Methode zum Entfernen eines "Likes" von einem Projekt
    // public void unlikeProject(Long projectId) {
    //     Project project = projectRepository.findById(projectId).orElse(null);
    //     if (project != null) {
    //         int currentLikes = project.getLikes();
    //         if (currentLikes > 0) {
    //             project.setLikes(currentLikes - 1);
    //             projectRepository.save(project);
    //         }
    //     }
    // }

    // // Methode zum Abrufen der Anzahl der Likes eines Projekts
    // public int getLikesCount(Long projectId) {
    //     Project project = projectRepository.findById(projectId).orElse(null);
    //     return project != null ? project.getLikes() : 0;
    // }
}
