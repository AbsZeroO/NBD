package org.example.model.domain;

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
    public static SegmentType fromString(String segmentType) {
        return switch (segmentType) {
            case "A" -> A;
            case "B" -> B;
            case "C" -> C;
            case "D" -> D;
            case "E" -> E;
            default -> throw new IllegalArgumentException("Unknown SegmentType: " + segmentType);
        };
    }
}
