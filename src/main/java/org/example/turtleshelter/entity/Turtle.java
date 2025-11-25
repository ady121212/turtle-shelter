package org.example.turtleshelter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "turtle")
public class Turtle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "species_id")
    private Species species;

    @Column(nullable = false)
    private String name;

    @Column(name = "intake_date", nullable = false)
    private ZonedDateTime intakeDate;

    @Column(name = "weight_grams", nullable = false)
    private Integer weight;

    @Column(name = "age_years", nullable = false)
    private Integer ageYears;

    @Column(nullable = false)
    private Boolean deleted = false;

    public Turtle() {
    }

    public Integer getId() {
        return id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(ZonedDateTime intakeDate) {
        this.intakeDate = intakeDate;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(Integer ageYears) {
        this.ageYears = ageYears;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turtle turtle = (Turtle) o;
        return Objects.equals(id, turtle.id) && Objects.equals(species, turtle.species) && Objects.equals(name, turtle.name) && Objects.equals(intakeDate, turtle.intakeDate) && Objects.equals(weight, turtle.weight) && Objects.equals(ageYears, turtle.ageYears) && Objects.equals(deleted, turtle.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, species, name, intakeDate, weight, ageYears, deleted);
    }

    @Override
    public String toString() {
        return "Turtle{" +
                "id=" + id +
                ", species=" + species +
                ", name='" + name + '\'' +
                ", intakeDate=" + intakeDate +
                ", weight=" + weight +
                ", ageYears=" + ageYears +
                ", deleted=" + deleted +
                '}';
    }
}
