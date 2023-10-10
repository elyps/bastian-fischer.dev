package com.sup1x.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sup1x.api.ApiApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.sup1x.api.repository.BuildInfoRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@Tag(name = "Version V1", description = "Version V1 Management API")
@RestController
@RequestMapping("/api/v1/version")
public class VersionController {

    @Autowired
    private BuildInfoRepository buildInfoRepository;

    // get build number from database
    @GetMapping("/build/number")
    public int getBuildNumber() {
        return buildInfoRepository.findById(1L).get().getBuildNumber();
    }

}
