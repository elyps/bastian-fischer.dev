package com.sup1x.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "build_info")
public class BuildInfo {
    
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatische ID-Generierung
    private Long id;
    private int buildNumber;

/*     // Konstruktor
    public BuildInfo() {
    }

    public BuildInfo(int buildNumber) {
        this.buildNumber = buildNumber;
    } */

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setId(Long id) {
        this.id = 1L;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

}
