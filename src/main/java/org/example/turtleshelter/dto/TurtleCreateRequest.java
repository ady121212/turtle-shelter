package org.example.turtleshelter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.ZonedDateTime;

public class TurtleCreateRequest {
    @NotNull(message = "speciesId required")
    private Long speciesId;

    @NotBlank(message = "name required")
    private String name;

    @NotNull(message = "intakeDate required")
    private ZonedDateTime intakeDate;

    @NotNull(message = "weightValue required")
    @Positive(message = "weightValue needs to be positive")
    private Double weightValue;

    @NotBlank(message = "weightUnit required")
    private String weightUnit;

    @NotNull(message = "ageYears required")
    @Min(value = 0, message = "ageYears cannot be negative")
    private Integer ageYears;

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
