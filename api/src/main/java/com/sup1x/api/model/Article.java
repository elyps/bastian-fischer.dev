package com.sup1x.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

//import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.util.List;


@Entity
//@RedisHash("article")
@Schema(description = "Article Model Information")
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "slug", nullable = false, length = 100)
    private String slug;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", nullable = false, length = 10000)
    private String content;

    @Column(name = "author", length = 50)
    private String author;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    /*@ElementCollection
    @Column(name = "tags", nullable = false)
    private List<String> tags;*/

/*    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Tag> tags = new HashSet<>();*/


    @ElementCollection
    @Column(name = "images", nullable = false)
    private List<String> images;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "url", nullable = false, length = 100)
    private String url;

    @ElementCollection
    @Column(name = "languages", nullable = false)
    private List<String> languages;

    @Column(name = "published", nullable = false)
    private Boolean published;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @Column(name="likes", nullable = true)
    private int likes;

    public Article() {
    }

    public Article(int id, String slug, String title, String content, String category, List<String> images, String description, String url, List<String> languages, Boolean published, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.content = content;
//        this.author = getUsername();
        this.category = category;
//        this.tags = tags;
        this.images = images;
        this.description = description;
        this.url = url;
        this.languages = languages;
        this.published = false;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

/*
    public Article(int id, String slug, String title, String content, String category, List<String> images, String description, String url, List<String> languages, Boolean published, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.content = content;
    }
*/

    public Article(int id, String slug, String title, String content) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.content = content;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public Set<Tag> getTags() {
//        return tags;
//    }

//    public void setTags(Set<Tag> tags) {
//        this.tags = tags;
//    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /*public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }*/

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", slug='").append(slug).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", category='").append(category).append('\'');
//        sb.append(", tags='").append(tags).append('\'');
        sb.append(", images='").append(images).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", languages='").append(languages).append('\'');
        sb.append(", published='").append(published).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", likes=").append(likes);
        sb.append('}');
        return sb.toString();
    }

}
