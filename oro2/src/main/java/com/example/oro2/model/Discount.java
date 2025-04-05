package com.example.oro2.model;

public enum Discount {
    NORMAL(1.0),
    REDUCED(0.7),
    STUDENT(0.5);

    private final double multiplier;

    Discount(double multiplier){
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
