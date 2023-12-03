package com.betrybe.fitness.controller;

import com.betrybe.fitness.dto.WorkoutCreationDto;
import com.betrybe.fitness.dto.WorkoutDto;
import com.betrybe.fitness.service.FitnessService;
import com.betrybe.fitness.service.FitnessServiceInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Fitness controller.
 */
@RestController
@RequestMapping("/fitness")
public class FitnessController implements FitnessControllerInterface {
  private FitnessServiceInterface service;

  @Autowired
  public FitnessController(FitnessServiceInterface service) {
    this.service = service;
  }

  @GetMapping
  public String getRoot() {
    return "Boas vindas à API de Fitness!";
  }

  /**
   * Gets workout.
   *
   * @param id the id
   * @return the workout
   */
  @GetMapping("/workouts/{id}")
  public ResponseEntity<WorkoutDto> getWorkout(@PathVariable Long id) {
    Optional<WorkoutDto> workoutDto = service.getWorkout(id);

    // Se estiver vazio, retorna status 404 (não encontrado)
    if (workoutDto.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // retorna status 200 (ok) com o DTO no corpo da resposta
    return ResponseEntity.ok(workoutDto.get());
  }

  /**
   * Create workout response entity.
   *
   * @param newWorkout the workout creation dto
   * @return the response entity
   */
  @PostMapping("/workouts")
  public ResponseEntity<WorkoutDto> createWorkout(@RequestBody WorkoutCreationDto newWorkout) {
    WorkoutDto newWorkoutDto = service.saveWorkout(newWorkout);
    return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutDto);
  }
}
