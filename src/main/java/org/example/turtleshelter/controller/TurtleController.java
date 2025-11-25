package org.example.turtleshelter.controller;

import jakarta.validation.Valid;
import org.example.turtleshelter.dto.TurtleCreateRequest;
import org.example.turtleshelter.dto.TurtleResponse;
import org.example.turtleshelter.dto.TurtleUpdateRequest;
import org.example.turtleshelter.service.TurtleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/turtles")
public class TurtleController {

    private final TurtleService turtleService;

    public TurtleController(TurtleService turtleService) {
        this.turtleService = turtleService;
    }

    @GetMapping("/all")
    public List<TurtleResponse> getAll() {
        return turtleService.getAll();
    }

    @GetMapping
    public TurtleResponse getOne(@RequestParam Long id) {
        return turtleService.getTurtle(id);
    }

    @GetMapping("/by-species")
    public List<TurtleResponse> getBySpecies(@RequestParam Long speciesId) {
        return turtleService.getBySpecies(speciesId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurtleResponse create(@Valid @RequestBody TurtleCreateRequest turtleCreateRequest) {
        return turtleService.create(turtleCreateRequest);
    }

    @PutMapping
    public TurtleResponse update(@Valid @RequestBody TurtleUpdateRequest turtleUpdateRequest) {
        return turtleService.update(turtleUpdateRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long id) {
        turtleService.delete(id);
    }
}
