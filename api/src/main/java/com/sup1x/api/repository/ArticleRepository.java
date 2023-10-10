package com.sup1x.api.repository;

import com.sup1x.api.model.Article;

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

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Page<Article> findByPublished(boolean published, Pageable pageable);

        // Rename one of the methods to make it unique
        Page<Article> findByPublished(boolean published, Pageable pageable);

        // find by slug
        Optional<Article> findArticleBySlug(String slug);
    
        // Keep the other method as it is
        List<Article> findByPublished(Boolean published);
    

    Page<Article> findByTitleContaining(String title, Pageable pageable);

    List<Article> findByTitleContaining(String title, Sort sort);
    List<Article> findByAuthorContaining(String author);
    List<Article> findByCategoryContaining(String category);

    List<Article> findByDescriptionContaining(String description);
    List<Article> findByUrlContaining(String url);

    // List<Article> findByPublished(Boolean published);
    List<Article> findByCreatedAtContaining(LocalDate createdAt);
    List<Article> findByUpdatedAtContaining(LocalDate updatedAt);

    List<Article> findByLanguagesIn(List<String> languages);
}
