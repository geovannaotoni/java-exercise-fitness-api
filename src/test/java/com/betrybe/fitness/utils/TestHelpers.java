package com.betrybe.fitness.utils;

import com.betrybe.fitness.dto.WorkoutCreationDto;
import com.betrybe.fitness.dto.WorkoutDto;
import com.betrybe.fitness.model.Workout;

public class TestHelpers {
  public static Workout createMockWorkout(Long id) {
    Workout workout = new Workout();
    workout.setId(id);
    workout.setName("workout name");
    workout.setRepetitions(123);
    workout.setSecretTechnique("very secret technique");

    return workout;
  }

  public static WorkoutDto modelToDto(Workout workout) {
    return new WorkoutDto(
        workout.getId(),
        workout.getName(),
        workout.getRepetitions()
    );
  }

  public static WorkoutCreationDto modelToCreationDto(Workout workout) {
    return new WorkoutCreationDto(
        workout.getName(),
        workout.getRepetitions(),
        workout.getSecretTechnique()
    );
  }
}
