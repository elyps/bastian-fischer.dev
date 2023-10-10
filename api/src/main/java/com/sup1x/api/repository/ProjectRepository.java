package com.sup1x.api.repository;

import com.sup1x.api.model.Project;

import com.sup1x.api.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Page<Project> findByPublished(boolean published, Pageable pageable);

    // Rename one of the methods to make it unique
    Page<Project> findByPublished(boolean published, Pageable pageable);

    // find by slug
    Optional<Project> findProjectBySlug(String slug);

    // Keep the other method as it is
    List<Project> findByPublished(Boolean published);

    Page<Project> findByTitleContaining(String title, Pageable pageable);

    List<Project> findByTitleContaining(String title, Sort sort);

    List<Project> findByAuthorContaining(String author);

    List<Project> findByCategoryContaining(String category);

    List<Project> findByDescriptionContaining(String description);

    List<Project> findByUrlContaining(String url);

    // List<Project> findByPublished(Boolean published);
    List<Project> findByCreatedAtContaining(LocalDate createdAt);

    List<Project> findByUpdatedAtContaining(LocalDate updatedAt);

    // List<Project> findByLanguagesIn(List<String> languages);
}
