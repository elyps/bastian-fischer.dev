package com.sup1x.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.sup1x.api.model.GitInfoProvider;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Git Info Provider V1", description = "The Git Info Provider V1 Management API")
public class GitInfoProviderController {

    @Autowired
    private GitInfoProvider gitInfoProvider;

    // constructor
    public GitInfoProviderController() {
      // TODO document why this constructor is empty
    }

    @GetMapping("/git/commit/id")
    public String getCommitId() throws IOException {
        return gitInfoProvider.getCommitId();
    }
    
}
