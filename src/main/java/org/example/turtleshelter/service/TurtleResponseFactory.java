package org.example.turtleshelter.service;

import org.example.turtleshelter.dto.TurtleResponse;
import org.example.turtleshelter.entity.Turtle;
import org.springframework.stereotype.Component;

@Component
public class TurtleResponseFactory {

    public TurtleResponse create(final Turtle turtle) {
        return TurtleResponse.builder()
                .withId(turtle.getId())
                .withSpeciesId(turtle.getSpecies().getId())
                .withSpeciesName(turtle.getSpecies().getName())
                .withHabitatTypeName(turtle.getSpecies().getHabitatType().getName())
                .withName(turtle.getName())
                .withIntakeDate(turtle.getIntakeDate())
                .withWeight(turtle.getWeight().longValue())
                .withAgeYears(turtle.getAgeYears())
                .withDeleted(turtle.getDeleted())
                .build();
    }
}
