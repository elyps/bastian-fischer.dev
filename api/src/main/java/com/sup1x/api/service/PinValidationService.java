package com.sup1x.api.service;

import com.sup1x.api.model.Pin;
import com.sup1x.api.repository.PinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PinValidationService {

    private static final Logger logger = LoggerFactory.getLogger(PinValidationService.class);

    @Autowired
    private PinRepository pinRepository;

    public PinValidationService(PinRepository pinRepository) {
        this.pinRepository = pinRepository;
    }

    public boolean isValid(int pin) {
        try {
            Pin enteredPin = pinRepository.findByPin(pin);
            logger.info("Entered PIN: " + pin);
            return enteredPin != null;
        } catch (Exception e) {
            // Log or handle the exception here.
            logger.error("An error occurred while validating PIN", e);
            return false;
        }
    }




}
