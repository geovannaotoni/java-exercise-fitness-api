package com.betrybe.fitness.service;

import com.betrybe.fitness.dto.WorkoutCreationDto;
import com.betrybe.fitness.dto.WorkoutDto;
import java.util.List;
import java.util.Optional;

/**
 * Interface for the Fitness service component.
 */
public interface FitnessServiceInterface {
  WorkoutDto saveWorkout(WorkoutCreationDto newWorkoutDto);

  Optional<WorkoutDto> getWorkout(Long id);

  List<WorkoutDto> getAllWorkouts();
}
