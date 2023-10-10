package com.sup1x.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Schema(description = "Tag Model Information")
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "article_id", nullable = false, length = 50)
    private int articleId;

    // Constructors
    public Tag() {

    }

    public Tag(Long id, String name, int articleId) {
        this.id = id;
        this.name = name;
        this.articleId = articleId;
    }

    public Tag(String name, int articleId) {
        this.name = name;
        this.articleId = articleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", articleId=").append(articleId);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag tag)) return false;

        if (getArticleId() != tag.getArticleId()) return false;
        if (!getId().equals(tag.getId())) return false;
        return getName() != null ? getName().equals(tag.getName()) : tag.getName() == null;
    }

}
