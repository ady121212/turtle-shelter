package org.example.turtleshelter.service;

import org.example.turtleshelter.dto.TurtleResponse;
import org.example.turtleshelter.entity.HabitatType;
import org.example.turtleshelter.entity.Species;
import org.example.turtleshelter.entity.Turtle;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TurtleResponseFactoryTest {

    private static final String TEST_SPECIES = "TestSpecies";
    private static final String TURTLE = "Turtle";
    private static final String SOSVIZ = "sósvíz";
    private static final LocalDate INTAKE_DATE = LocalDate.of(2023, 1, 1);
    private static final int WEIGHT = 150;
    private static final int AGE_YEARS = 5;
    private static final boolean DELETED = false;
    private static final long EXPECTED_WEIGHT = 150L;

    private final TurtleResponseFactory underTest = new TurtleResponseFactory();

    @Test
    void testCreateShouldReturnTurtleResponseWithCorrectValues() {
        HabitatType habitatType = createHabitatType();
        Species species = createSpecies(habitatType);
        Turtle turtle = createTurtle(species);

        TurtleResponse response = underTest.create(turtle);

        assertNotNull(response);
        assertEquals(TEST_SPECIES, response.getSpeciesName());
        assertEquals(SOSVIZ, response.getHabitatTypeName());
        assertEquals(TURTLE, response.getName());
        assertEquals(INTAKE_DATE, response.getIntakeDate().toLocalDate());
        assertEquals(EXPECTED_WEIGHT, response.getWeight());
        assertEquals(AGE_YEARS, response.getAgeYears());
        assertFalse(response.getDeleted());
    }

    private HabitatType createHabitatType() {
        HabitatType habitatType = new HabitatType();
        habitatType.setName(SOSVIZ);
        return habitatType;
    }

    private Species createSpecies(HabitatType habitatType) {
        Species species = new Species();
        species.setName(TEST_SPECIES);
        species.setHabitatType(habitatType);
        return species;
    }

    private Turtle createTurtle(Species species) {
        Turtle turtle = new Turtle();
        turtle.setSpecies(species);
        turtle.setName(TURTLE);
        turtle.setIntakeDate(INTAKE_DATE.atStartOfDay(ZoneId.of("UTC")));
        turtle.setWeight(WEIGHT);
        turtle.setAgeYears(AGE_YEARS);
        turtle.setDeleted(DELETED);
        return turtle;
    }

}