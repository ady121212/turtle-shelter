package org.example.turtleshelter.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightConverterTest {

    private static final int EXPECTED_CONVERTED_OUNCES = 57;
    private static final int EXPECTED_CONVERTED_POUNDS = 907;
    private static final int EXPECTED_CONVERTED_KILOGRAM = 2000;
    private static final int EXPECTED_CONVERTED_GRAMS = 500;
    private static final int GRAM_VALUE = 500;
    private static final int INPUT_VALUE = 2;

    private final WeightConverter underTest = new WeightConverter();

    @Test
    void testsToGramsShouldConvertsGramsCorrectly() {
        long actual = underTest.toGrams(GRAM_VALUE, WeightUnit.G);

        assertEquals(EXPECTED_CONVERTED_GRAMS, actual);
    }

    @Test
    void testsToGramsShouldConvertsKilogramsToGrams() {
        long actual = underTest.toGrams(INPUT_VALUE, WeightUnit.KG);

        assertEquals(EXPECTED_CONVERTED_KILOGRAM, actual);
    }

    @Test
    void testsToGramsShouldConvertsPoundsToGrams() {
        long actual = underTest.toGrams(2, WeightUnit.LB);

        assertEquals(EXPECTED_CONVERTED_POUNDS, actual);
    }

    @Test
    void testsToGramsShouldConvertsOuncesToGrams() {
        long actual = underTest.toGrams(2, WeightUnit.OZ);

        assertEquals(EXPECTED_CONVERTED_OUNCES, actual);
    }
}