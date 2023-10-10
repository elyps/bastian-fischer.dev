package com.sup1x.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pins")
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pin")
    private int pin;

    public Pin(Long id, int pin) {
        this.id = id;
        this.pin = pin;
    }

    public Pin(int pin) {
        this.pin = pin;
    }

    public Pin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return pin;
    }

    public void setValue(int pin) {
        this.pin = pin;
    }
}
