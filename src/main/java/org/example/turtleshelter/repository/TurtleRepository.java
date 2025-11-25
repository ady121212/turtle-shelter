package org.example.turtleshelter.repository;

import org.example.turtleshelter.entity.Species;
import org.example.turtleshelter.entity.Turtle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurtleRepository extends JpaRepository<Turtle, Long> {
    List<Turtle> findByDeletedFalse();
    List<Turtle> findBySpeciesAndDeletedFalse(Species species);
}
