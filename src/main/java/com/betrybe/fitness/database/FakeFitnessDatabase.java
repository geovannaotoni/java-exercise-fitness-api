package com.betrybe.fitness.database;

import com.betrybe.fitness.model.Workout;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class that simulates a fake database/repository.
 */
@Component
public class FakeFitnessDatabase {
  private final Map<Long, Workout> workouts;

  @Autowired
  FakeFitnessDatabase(WorkoutsLoader workoutsLoader) {
    List<Workout> workoutList = workoutsLoader.fromClasspath("data/workouts.json");
    workouts = workoutList.stream().collect(Collectors.toMap(Workout::getId, Function.identity()));
  }

  public List<Workout> getAllWorkouts() {
    return workouts.values().stream().toList();
  }

  public Optional<Workout> getWorkout(Long id) {
    return Optional.ofNullable(workouts.get(id));
  }


  /**
   * Takes a workout object, assigns it an ID if it doesn't already have one,
   * and saves to the fake database. Always returns a new object (the saved one).
   */
  public Workout saveWorkout(Workout workout) {
    Workout newWorkout = new Workout();
    newWorkout.setId(workout.getId());
    newWorkout.setName(workout.getName());
    newWorkout.setRepetitions(workout.getRepetitions());
    newWorkout.setSecretTechnique(workout.getSecretTechnique());

    if (newWorkout.getId() == null) {
      Long newId = workouts.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
      newWorkout.setId(newId);
    }

    workouts.put(newWorkout.getId(), newWorkout);

    return newWorkout;
  }

  public Workout deleteWorkout(Long id) {
    return workouts.remove(id);
  }
}
