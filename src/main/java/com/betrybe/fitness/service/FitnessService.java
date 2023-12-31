package com.betrybe.fitness.service;

import com.betrybe.fitness.database.FakeFitnessDatabase;
import com.betrybe.fitness.dto.WorkoutCreationDto;
import com.betrybe.fitness.dto.WorkoutDto;
import com.betrybe.fitness.model.Workout;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Fitness service.
 */
@Service
public class FitnessService implements FitnessServiceInterface {
  private FakeFitnessDatabase database;

  @Autowired
  public FitnessService(FakeFitnessDatabase database) {
    this.database = database;
  }

  @Override
  public WorkoutDto saveWorkout(WorkoutCreationDto newWorkoutDto) {
    Workout workout = new Workout();
    workout.setName(newWorkoutDto.name());
    workout.setRepetitions(newWorkoutDto.repetitions());
    workout.setSecretTechnique(newWorkoutDto.secretTechnique());

    Workout newWorkout = database.saveWorkout(workout);

    return new WorkoutDto(
      newWorkout.getId(),
      newWorkout.getName(),
      newWorkout.getRepetitions()
    );
  }

  @Override
  public Optional<WorkoutDto> getWorkout(Long id) {
    Optional<Workout> workout = database.getWorkout(id);

    // se o resultado estiver vazio
    if (workout.isEmpty()) {
      return Optional.empty();
    }

    WorkoutDto workoutDto = new WorkoutDto(
        workout.get().getId(),
        workout.get().getName(),
        workout.get().getRepetitions()
    );

    // retorna um objeto Optional do DTO
    return Optional.of(workoutDto);
  }

  @Override
  public List<WorkoutDto> getAllWorkouts() {
    List<WorkoutDto> allWorkouts = new ArrayList<>();

    for (Workout workout : database.getAllWorkouts()) {
      allWorkouts.add(new WorkoutDto(
          workout.getId(),
          workout.getName(),
          workout.getRepetitions()
      ));
    }
    return allWorkouts;
  }
}
