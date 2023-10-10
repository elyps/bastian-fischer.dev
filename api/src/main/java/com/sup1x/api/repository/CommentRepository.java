package com.sup1x.api.repository;

import com.sup1x.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleId(Long articleId);

//    List<Comment> findByArticleId(Long articleId);

//    List<Comment> findByArticleTitle(String articleTitle);

//    List<Comment> findByArticleName(String articleName);

//    List<Comment> findByAuthor(String author);

}
