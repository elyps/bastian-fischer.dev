package com.sup1x.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUser {
    private String login;
    private String name;
    private String bio;
    // Weitere Felder, die Sie benötigen, hier hinzufügen

    // Standard-Getter und -Setter für die Felder

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
    // Weitere Getter und Setter für zusätzliche Felder
}
