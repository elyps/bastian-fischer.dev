package com.sup1x.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.PropertySource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.ObjectId;
import java.io.File;
import java.io.IOException;

// @Entity
@Component
// @PropertySource("classpath:git.properties")
public class GitInfoProvider{

    public String getCommitId() throws IOException {
        try (Repository repository = Git.open(new File(".").getAbsoluteFile()).getRepository()) {
            ObjectId head = repository.resolve("HEAD");
            return head.getName();
        }
    }
}

