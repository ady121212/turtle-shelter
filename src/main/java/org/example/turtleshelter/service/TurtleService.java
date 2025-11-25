package org.example.turtleshelter.service;

import org.example.turtleshelter.dto.TurtleCreateRequest;
import org.example.turtleshelter.dto.TurtleResponse;
import org.example.turtleshelter.dto.TurtleUpdateRequest;
import org.example.turtleshelter.entity.Species;
import org.example.turtleshelter.entity.Turtle;
import org.example.turtleshelter.exception.BadRequestException;
import org.example.turtleshelter.exception.NotFoundException;
import org.example.turtleshelter.repository.SpeciesRepository;
import org.example.turtleshelter.repository.TurtleRepository;
import org.example.turtleshelter.util.WeightConverter;
import org.example.turtleshelter.util.WeightUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurtleService {
    private final TurtleRepository turtleRepository;
    private final SpeciesRepository speciesRepository;
    private final WeightConverter weightConverter;
    private final TurtleResponseFactory turtleResponseFactory;

    @Autowired
    public TurtleService(TurtleRepository turtleRepository, SpeciesRepository speciesRepository, WeightConverter weightConverter,
                         TurtleResponseFactory turtleResponseFactory) {
        this.turtleRepository = turtleRepository;
        this.speciesRepository = speciesRepository;
        this.weightConverter = weightConverter;
        this.turtleResponseFactory = turtleResponseFactory;
    }

    public List<TurtleResponse> getAll() {
        return turtleRepository.findByDeletedFalse()
                .stream()
                .map(turtleResponseFactory::create)
                .collect(Collectors.toList());
    }

    public TurtleResponse getTurtle(Long id) {
        Turtle turtle = findTurtleById(id);
        if (turtle.getDeleted()) throw new NotFoundException("Turtle is deleted: id=" + id);
        return turtleResponseFactory.create(turtle);
    }

    public List<TurtleResponse> getBySpecies(Long speciesId) {
        Species species = findSpeciesById(speciesId);
        return turtleRepository.findBySpeciesAndDeletedFalse(species)
                .stream()
                .map(turtleResponseFactory::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public TurtleResponse create(TurtleCreateRequest request) {
        Species species = findSpeciesById(request.getSpeciesId());
        WeightUnit unit = parseWeightUnit(request.getWeightUnit());
        long grams = weightConverter.toGrams(request.getWeightValue(), unit);

        Turtle turtle = new Turtle();
        turtle.setSpecies(species);
        turtle.setName(request.getName());
        turtle.setIntakeDate(request.getIntakeDate());
        turtle.setWeight(Math.toIntExact(grams));
        turtle.setAgeYears(request.getAgeYears());
        turtle.setDeleted(false);

        return turtleResponseFactory.create(turtleRepository.save(turtle));
    }

    @Transactional
    public TurtleResponse update(TurtleUpdateRequest request) {
        Turtle turtle = findTurtleById(request.getId());
        if (turtle.getDeleted()) {
            throw new NotFoundException("Cannot modify deleted turtle: id=" + request.getId());
        }

        Long speciesId = request.getSpeciesId();
        if (speciesId != null) {
            turtle.setSpecies(findSpeciesById(speciesId));
        }
        String name = request.getName();
        if (name != null) {
            turtle.setName(name);
        }
        ZonedDateTime intakeDate = request.getIntakeDate();
        if (intakeDate != null) {
            turtle.setIntakeDate(intakeDate);
        }
        Integer ageYears = request.getAgeYears();
        if (ageYears != null) {
            turtle.setAgeYears(ageYears);
        }

        if (request.getWeightValue() != null || request.getWeightUnit() != null) {
            WeightUnit unit = parseWeightUnit(request.getWeightUnit());
            long grams = weightConverter.toGrams(request.getWeightValue(), unit);
            turtle.setWeight(Math.toIntExact(grams));
        }

        return turtleResponseFactory.create(turtleRepository.save(turtle));
    }

    @Transactional
    public void delete(Long id) {
        Turtle turtle = findTurtleById(id);
        if ((turtle.getDeleted())) {
            throw new NotFoundException("Turtle already deleted: id=" + id);
        }
        turtle.setDeleted(true);
        turtleRepository.save(turtle);
    }

    private Turtle findTurtleById(Long id) {
        return turtleRepository.findById(id).orElseThrow(() -> new NotFoundException("Turtle not found: id=" + id));
    }

    private Species findSpeciesById(Long id) {
        return speciesRepository.findById(id).orElseThrow(() -> new BadRequestException("Species not found: id=" + id));
    }

    private WeightUnit parseWeightUnit(String weightUnit) {
        try {
            return weightConverter.fromString(weightUnit);
        } catch (Exception exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

}