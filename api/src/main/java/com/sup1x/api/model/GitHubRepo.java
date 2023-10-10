package com.sup1x.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "github_repo")
public class GitHubRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT", length = 11, nullable = false, unique = true, updatable = false, insertable = false, name = "id")
    private Long id;

    @Column(columnDefinition = "TEXT", length = 65535, nullable = true, unique = false, updatable = true, insertable = true, name = "name")
    private String name;

    @Column(columnDefinition = "TEXT", length = 65535, nullable = true, unique = false, updatable = true, insertable = true, name = "description")
    private String description;

    // @Column(nullable = true, name = "readme")
    // private String readMe;

    @Column(columnDefinition = "TEXT", length = 65535, nullable = true, unique = false, updatable = true, insertable = true, name = "readme_content")
    private String readmeContent;

    /*
     * // Konstruktor
     * public GitHubRepo() {
     * }
     * 
     * public GitHubRepo(String name, String description, String readme) {
     * this.name = name;
     * this.description = description;
     * this.readme = readme;
     * }
     */

    // Getter und Setter
    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    // /* *
    // * @return String return the readme
    // */
    // public String getReadMe() {
    // return readMe;
    // }

    // /**
    // * @param readMe the readme to set
    // */
    // public void setReadMe(String readMe) {
    // if (readMe == null) {
    // this.readMe = "test";
    // } else {
    // this.readMe = readMe;
    // }
    // }

    /**
     * @return String return the readmeContent
     */
    public String getReadmeContent() {
        return readmeContent;
    }

    /**
     * @param readmeContent the readmeContent to set
     */
    public void setReadmeContent(String readmeContent) {
        this.readmeContent = readmeContent;
    }

}
