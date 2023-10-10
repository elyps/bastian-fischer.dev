package com.sup1x.api.controller;

import com.sup1x.api.model.Pin;
import com.sup1x.api.repository.PinRepository;
import com.sup1x.api.service.PinValidationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pin")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Pins", description = "Pin V1 Management API")
public class PinValidationController {

    @Autowired
    private PinValidationService pinValidationService;

    @Autowired
    private PinRepository pinRepository;

    public PinValidationController(PinValidationService pinValidationService, PinRepository pinRepository) {
        this.pinValidationService = pinValidationService;
        this.pinRepository = pinRepository;
    }

    @PostMapping("/validate/{pin}")
    public boolean validatePin(@PathVariable int pin) {
        return pinValidationService.isValid(pin);
    }

    /*@GetMapping("/validate/{value}")
    public boolean validatePin(@PathVariable String value) {
        return pinValidationService.isValid(value);
    }*/

/*    @GetMapping("/get/{id}")
    public String getPin(@PathVariable Long id) {
        return pinRepository.findById(id).get().getValue();
    }

    @PostMapping("/add/{pin}")
    public void addPin(@PathVariable String pin) {
        pinRepository.save(new Pin(pin));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePin(@PathVariable Long id) {
        pinRepository.deleteById(id);
    }

    @PutMapping("/update/{id}/{pin}")
    public void updatePin(@PathVariable Long id, @PathVariable String pin) {
        Pin p = pinRepository.findById(id).get();
        p.setValue(pin);
        pinRepository.save(p);
    }

    @GetMapping("/getall")
    public List<Pin> getAllPins() {
        return pinRepository.findAll();
    }*/

}
