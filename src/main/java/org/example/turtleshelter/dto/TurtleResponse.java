package org.example.turtleshelter.dto;

import java.time.ZonedDateTime;

public class TurtleResponse {
    private final Integer id;
    private final Integer speciesId;
    private final String speciesName;
    private final String habitatTypeName;
    private final String name;
    private final ZonedDateTime intakeDate;
    private final Long weight;
    private final Integer ageYears;
    private final Boolean deleted;

    private TurtleResponse(Builder builder) {
        this.id = builder.id;
        this.speciesId = builder.speciesId;
        this.speciesName = builder.speciesName;
        this.habitatTypeName = builder.habitatTypeName;
        this.name = builder.name;
        this.intakeDate = builder.intakeDate;
        this.weight = builder.weight;
        this.ageYears = builder.ageYears;
        this.deleted = builder.deleted;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public String getHabitatTypeName() {
        return habitatTypeName;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getIntakeDate() {
        return intakeDate;
    }

    public Long getWeight() {
        return weight;
    }

    public Integer getAgeYears() {
        return ageYears;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private Integer speciesId;
        private String speciesName;
        private String habitatTypeName;
        private String name;
        private ZonedDateTime intakeDate;
        private Long weight;
        private Integer ageYears;
        private Boolean deleted;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withSpeciesId(Integer speciesId) {
            this.speciesId = speciesId;
            return this;
        }

        public Builder withSpeciesName(String speciesName) {
            this.speciesName = speciesName;
            return this;
        }

        public Builder withHabitatTypeName(String habitatTypeName) {
            this.habitatTypeName = habitatTypeName;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIntakeDate(ZonedDateTime intakeDate) {
            this.intakeDate = intakeDate;
            return this;
        }

        public Builder withWeight(Long weight) {
            this.weight = weight;
            return this;
        }

        public Builder withAgeYears(Integer ageYears) {
            this.ageYears = ageYears;
            return this;
        }

        public Builder withDeleted(Boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public TurtleResponse build() {
            return new TurtleResponse(this);
        }
    }
}
