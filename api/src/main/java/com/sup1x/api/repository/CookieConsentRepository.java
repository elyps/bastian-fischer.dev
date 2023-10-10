package com.sup1x.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sup1x.api.model.CookieConsent;

@Repository
public interface CookieConsentRepository extends JpaRepository<CookieConsent, Long> {

    CookieConsent findByUserId(Long userId);
}
