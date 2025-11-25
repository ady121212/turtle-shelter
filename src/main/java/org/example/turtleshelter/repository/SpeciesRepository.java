package org.example.turtleshelter.repository;

import org.example.turtleshelter.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpeciesRepository extends JpaRepository<Species, Long> {
}