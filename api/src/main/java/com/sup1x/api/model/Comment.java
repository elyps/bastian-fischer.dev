package com.sup1x.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Schema(description = "Comment Model Information")
@Table(name = "comments")
public class Comment {

    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 10000)
    private String content;

    @Column(name = "comment_author", length = 50)
    private String commentAuthor;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

/*    @Column(name = "article_id")
    @JoinTable(name = "article", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "article_id"))
//    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    private Long articleId;*/

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment(String content, String commentAuthor, String createdAt, String updatedAt) {
        this.content = content;
        this.commentAuthor = commentAuthor;
        this.createdAt = LocalDate.now() + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds();
        this.updatedAt = LocalDate.now() + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds();
    }

    public Comment(String content) {
        this.content = content;
    }

    // getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Article getArticle() {
        return article;
    }

    // Constructors
    public Comment() {

    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
