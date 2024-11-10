package org.example.model;

public enum SegmentType {
    A(1.0),
    B(1.1),
    C(1.2),
    D(1.3),
    E(2.0);

    private final double multiplier;

    SegmentType(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
