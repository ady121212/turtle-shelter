package org.example.turtleshelter.service;

import org.example.turtleshelter.dto.TurtleCreateRequest;
import org.example.turtleshelter.dto.TurtleResponse;
import org.example.turtleshelter.dto.TurtleUpdateRequest;
import org.example.turtleshelter.entity.HabitatType;
import org.example.turtleshelter.entity.Species;
import org.example.turtleshelter.entity.Turtle;
import org.example.turtleshelter.exception.BadRequestException;
import org.example.turtleshelter.exception.NotFoundException;
import org.example.turtleshelter.repository.SpeciesRepository;
import org.example.turtleshelter.repository.TurtleRepository;
import org.example.turtleshelter.util.WeightConverter;
import org.example.turtleshelter.util.WeightUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TurtleServiceTest {

    private static final String GRAM = "G";
    private static final long EXPECTED_WEIGHT_100 = 100L;
    private static final long EXPECTED_WEIGHT_200 = 200L;
    private static final String UPDATED_NAME = "Updated Name";
    private static final String TEST_SPECIES_NAME = "TestSpecies";
    private static final String TURTLE_1 = "Turtle1";
    private static final boolean DELETED = false;
    private static final String TURTLE_2 = "Turtle2";
    private static final int WEIGHT_100 = 100;
    private static final int WEIGHT_200 = 200;
    private static final long ID = 1L;

    @Mock
    private TurtleRepository turtleRepository;

    @Mock
    private SpeciesRepository speciesRepository;

    @Mock
    private WeightConverter weightConverter;

    @Mock
    private TurtleResponseFactory turtleResponseFactory;

    private TurtleService turtleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        turtleService = new TurtleService(turtleRepository, speciesRepository, weightConverter, turtleResponseFactory);
    }

    @Test
    void testGetOneShouldThrowsNotFoundExceptionWhenTurtleDoesNotExist() {
        when(turtleRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> turtleService.getTurtle(ID));
    }

    @Test
    void testCreateShouldThrowsBadRequestExceptionWhenSpeciesNotFound() {
        TurtleCreateRequest request = new TurtleCreateRequest();
        request.setSpeciesId(ID);
        when(speciesRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> turtleService.create(request));
    }

    @Test
    void testUpdateShouldThrowsNotFoundExceptionWhenTurtleIsDeleted() {
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);
        turtle.setDeleted(true);
        when(turtleRepository.findById(ID)).thenReturn(Optional.of(turtle));
        TurtleUpdateRequest request = new TurtleUpdateRequest();
        request.setId(ID);

        assertThrows(NotFoundException.class, () -> turtleService.update(request));
    }

    @Test
    void testDeleteShouldThrowsNotFoundExceptionWhenTurtleAlreadyDeleted() {
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);
        turtle.setDeleted(true);
        when(turtleRepository.findById(ID)).thenReturn(Optional.of(turtle));

        assertThrows(NotFoundException.class, () -> turtleService.delete(ID));
    }

    @Test
    void testCreateShouldSuccessfullyCreateTurtle() {
        TurtleCreateRequest request = new TurtleCreateRequest();
        request.setSpeciesId(ID);
        request.setName(TURTLE_1);
        request.setWeightValue(100.0);
        request.setWeightUnit(GRAM);
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);
        when(speciesRepository.findById(ID)).thenReturn(Optional.of(species));
        when(weightConverter.fromString(GRAM)).thenReturn(WeightUnit.G);
        when(weightConverter.toGrams(WEIGHT_100, WeightUnit.G)).thenReturn(EXPECTED_WEIGHT_100);
        when(turtleRepository.save(turtle)).thenReturn(turtle);
        when(turtleResponseFactory.create(turtle)).thenReturn(createTurtleResponse(TURTLE_1, TEST_SPECIES_NAME, EXPECTED_WEIGHT_100));

        TurtleResponse actual = turtleService.create(request);

        verify(speciesRepository).findById(ID);
        verify(weightConverter).fromString(GRAM);
        verify(weightConverter).toGrams(WEIGHT_100, WeightUnit.G);
        verify(turtleRepository).save(turtle);
        verify(turtleResponseFactory).create(turtle);

        assertNotNull(actual);
    }

    @Test
    void updateSuccessfullyUpdatesTurtleWhenValidRequestProvided() {
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);
        TurtleUpdateRequest request = new TurtleUpdateRequest();
        request.setId(ID);
        request.setName(UPDATED_NAME);

        when(turtleRepository.findById(ID)).thenReturn(Optional.of(turtle));
        when(turtleRepository.save(turtle)).thenReturn(turtle);
        when(turtleResponseFactory.create(turtle)).thenReturn(createTurtleResponse(UPDATED_NAME, TEST_SPECIES_NAME, EXPECTED_WEIGHT_100));

        TurtleResponse response = turtleService.update(request);

        verify(turtleRepository).findById(ID);
        verify(turtleRepository).save(turtle);
        verify(turtleResponseFactory).create(turtle);
        assertNotNull(response);
        assertEquals(UPDATED_NAME, response.getName());
    }

    @Test
    void deleteSuccessfullyMarksTurtleAsDeleted() {
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);
        turtle.setDeleted(DELETED);
        when(turtleRepository.findById(ID)).thenReturn(Optional.of(turtle));

        turtleService.delete(ID);

        verify(turtleRepository).save(turtle);
        assertTrue(turtle.getDeleted());
    }

    @Test
    void getBySpeciesReturnsEmptyListWhenNoTurtlesExistForSpecies() {
        Species species = createSpecies();
        when(speciesRepository.findById(ID)).thenReturn(Optional.of(species));
        when(turtleRepository.findBySpeciesAndDeletedFalse(species)).thenReturn(List.of());

        List<TurtleResponse> result = turtleService.getBySpecies(ID);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllReturnsListOfTurtleResponsesForNonDeletedTurtles() {
        Species species = createSpecies();
        Turtle turtle1 = createTurtle(species, TURTLE_1, WEIGHT_100);
        Turtle turtle2 = createTurtle(species, TURTLE_2, WEIGHT_200);

        when(turtleRepository.findByDeletedFalse()).thenReturn(List.of(turtle1, turtle2));
        when(turtleResponseFactory.create(turtle1)).thenReturn(createTurtleResponse(TURTLE_1, TEST_SPECIES_NAME, EXPECTED_WEIGHT_100));
        when(turtleResponseFactory.create(turtle2)).thenReturn(createTurtleResponse(TURTLE_2, TEST_SPECIES_NAME, EXPECTED_WEIGHT_200));

        List<TurtleResponse> actual = turtleService.getAll();

        verify(turtleRepository).findByDeletedFalse();
        verify(turtleResponseFactory).create(turtle1);
        verify(turtleResponseFactory).create(turtle2);
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertTrue(actual.stream().anyMatch(turtle -> turtle.getName().equals(TURTLE_1)));
        assertTrue(actual.stream().anyMatch(turtle -> turtle.getName().equals(TURTLE_2)));
    }

    @Test
    void getOneReturnsTurtleResponseWhenTurtleExistsAndNotDeleted() {
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);
        when(turtleRepository.findById(ID)).thenReturn(Optional.of(turtle));
        when(turtleResponseFactory.create(turtle)).thenReturn(createTurtleResponse(TURTLE_1, TEST_SPECIES_NAME, EXPECTED_WEIGHT_100));

        TurtleResponse response = turtleService.getTurtle(ID);

        verify(turtleRepository).findById(ID);
        verify(turtleResponseFactory).create(turtle);
        assertNotNull(response);
        assertEquals(TURTLE_1, response.getName());
        assertEquals(TEST_SPECIES_NAME, response.getSpeciesName());
        assertEquals(EXPECTED_WEIGHT_100, response.getWeight());
    }

    @Test
    void getBySpeciesReturnsTurtleResponsesForExistingSpecies() {
        Species species = createSpecies();
        Turtle turtle = createTurtle(species, TURTLE_1, WEIGHT_100);

        when(speciesRepository.findById(ID)).thenReturn(Optional.of(species));
        when(turtleRepository.findBySpeciesAndDeletedFalse(species)).thenReturn(List.of(turtle));
        when(turtleResponseFactory.create(turtle)).thenReturn(createTurtleResponse(TURTLE_1, TEST_SPECIES_NAME, EXPECTED_WEIGHT_100));

        List<TurtleResponse> result = turtleService.getBySpecies(ID);

        verify(speciesRepository).findById(ID);
        verify(turtleRepository).findBySpeciesAndDeletedFalse(species);
        verify(turtleResponseFactory).create(turtle);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TURTLE_1, result.get(0).getName());
        assertEquals(TEST_SPECIES_NAME, result.get(0).getSpeciesName());
    }

    private Turtle createTurtle(Species species, String name, int weight) {
        Turtle turtle = new Turtle();
        turtle.setSpecies(species);
        turtle.setName(name);
        turtle.setDeleted(false);
        turtle.setWeight(weight);
        return turtle;
    }

    private Species createSpecies() {
        Species species = new Species();
        species.setName(TEST_SPECIES_NAME);
        species.setHabitatType(new HabitatType());
        return species;
    }

    private TurtleResponse createTurtleResponse(String name, String speciesName, long weight) {
        return TurtleResponse.builder()
                .withName(name)
                .withSpeciesName(speciesName)
                .withWeight(weight)
                .build();
    }
}