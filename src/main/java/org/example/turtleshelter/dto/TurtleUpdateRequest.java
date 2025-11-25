package org.example.turtleshelter.dto;

import jakarta.validation.constraints.NotNull;

import java.time.ZonedDateTime;

public class TurtleUpdateRequest {
    @NotNull(message = "id required for update")
    private Long id;

    private Long speciesId;
    private String name;
    private ZonedDateTime intakeDate;
    private Double weightValue;
    private String weightUnit;
    private Integer ageYears;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Long speciesId) {
        this.speciesId = speciesId;
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

    public Double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Double weightValue) {
        this.weightValue = weightValue;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Integer getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(Integer ageYears) {
        this.ageYears = ageYears;
    }
}
