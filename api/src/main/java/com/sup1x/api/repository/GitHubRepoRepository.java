package com.sup1x.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sup1x.api.model.GitHubRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitHubRepoRepository extends JpaRepository<GitHubRepo, Long> {
    // Benutzerdefinierte Abfragen, um Daten zu speichern oder abzurufen
}
