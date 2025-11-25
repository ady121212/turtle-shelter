package org.example.turtleshelter.util;

import org.springframework.stereotype.Component;

import static org.example.turtleshelter.util.WeightUnit.G;
import static org.example.turtleshelter.util.WeightUnit.KG;
import static org.example.turtleshelter.util.WeightUnit.LB;
import static org.example.turtleshelter.util.WeightUnit.OZ;

@Component
public class WeightConverter {
    public long toGrams(double value, WeightUnit unit) {
        return switch (unit) {
            case G -> Math.round(value);
            case KG -> Math.round(value * 1000.0);
            case LB -> Math.round(value * 453.59237);
            case OZ -> Math.round(value * 28.349523125);
        };
    }

    public WeightUnit fromString(String unitString) {
        if (unitString == null) throw new IllegalArgumentException("weightUnit null");
        return switch (unitString.trim().toLowerCase()) {
            case "g", "gram", "gramm" -> G;
            case "kg", "kilogram" -> KG;
            case "lb", "lbs", "pound" -> LB;
            case "oz", "ounce" -> OZ;
            default -> throw new IllegalArgumentException("Unknown weightUnit: " + unitString);
        };
    }
}
