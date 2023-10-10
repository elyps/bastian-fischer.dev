package com.sup1x.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sup1x.api.model.CookieConsent;
import com.sup1x.api.repository.CookieConsentRepository;

//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@Tag(name = "Cookies V1", description = "Cookies V1 Management API")
@RestController
@RequestMapping("/api/v1/cookies")
public class CookieController {

    private final CookieConsentRepository cookieConsentRepository;

    @Autowired
    public CookieController(CookieConsentRepository cookieConsentRepository) {
        this.cookieConsentRepository = cookieConsentRepository;
    }

    @PostMapping("/save")
    public String setCookieConsent(@RequestBody CookieConsent consent) {
        cookieConsentRepository.save(consent);
        return "Cookie-Zustimmung gespeichert.";
    }

    @GetMapping("/manage")
    public String setCookieConsent(@RequestParam boolean consent) {
        if (consent) {
            // Speichere Zustimmung im Local Storage
            return "Cookies werden akzeptiert. Zustimmung gespeichert.";
        } else {
            // Lösche eventuell gespeicherte Zustimmung im Local Storage
            return "Cookies werden nicht akzeptiert. Zustimmung entfernt.";
        }
    }
}
