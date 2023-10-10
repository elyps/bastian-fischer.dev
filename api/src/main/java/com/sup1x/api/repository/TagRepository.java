package com.sup1x.api.repository;

import com.sup1x.api.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteByName(String name);

    Optional<Tag> findByName(String name);

    List<Tag> findByArticleId(int articleId);

    boolean existsByName(String name);

//    List<Tag> findByArticleName(String articleName);

//    List<Tag> findByArticleSlug(String articleSlug);

//    List<Tag> findByArticleTitle(String articleTitle);
}
