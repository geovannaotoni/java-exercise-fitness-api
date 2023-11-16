package com.betrybe.fitness;

import static com.betrybe.fitness.utils.TestHelpers.createMockWorkout;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import com.betrybe.fitness.database.FakeFitnessDatabase;
import com.betrybe.fitness.database.WorkoutsLoader;
import com.betrybe.fitness.dto.WorkoutCreationDto;
import com.betrybe.fitness.dto.WorkoutDto;
import com.betrybe.fitness.model.Workout;
import com.betrybe.fitness.service.FitnessServiceInterface;
import com.betrybe.fitness.utils.TestHelpers;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ServiceLayerTest {
  @Autowired
  WorkoutsLoader workoutsLoader;

  @MockBean
  FakeFitnessDatabase fakeDatabase;

  @Autowired
  FitnessServiceInterface workoutsService;

  @Test
  @DisplayName("5 - Camada de serviço: método getWorkout implementado corretamente")
  void testGetWorkout() {
    Workout workout = createMockWorkout(44L);
    Mockito.when(fakeDatabase.getWorkout(workout.getId())).thenReturn(Optional.of(workout));
    Optional<WorkoutDto> returnedWorkout = workoutsService.getWorkout(workout.getId());
    Mockito.verify(fakeDatabase).getWorkout(workout.getId());

    assertTrue(returnedWorkout.isPresent());
    assertEquals(TestHelpers.modelToDto(workout), returnedWorkout.get());

    Mockito.when(fakeDatabase.getWorkout(55L)).thenReturn(Optional.empty());
    assertTrue(workoutsService.getWorkout(55L).isEmpty());

    Mockito.verify(fakeDatabase).getWorkout(55L);
  }

  @Test
  @DisplayName("7 - Camada de serviço: método saveWorkout implementado corretamente")
  void testWorkoutCreation() {
    Workout newWorkout = createMockWorkout(33L);
    Mockito.when(fakeDatabase.saveWorkout(any())).thenReturn(newWorkout);

    WorkoutCreationDto toSaveWorkout = TestHelpers.modelToCreationDto(newWorkout);
    WorkoutDto createdWorkout = workoutsService.saveWorkout(toSaveWorkout);

    WorkoutDto expectedWorkout = TestHelpers.modelToDto(newWorkout);
    assertEquals(expectedWorkout, createdWorkout);

    Mockito.verify(fakeDatabase).saveWorkout(any());
  }

  @Test
  @DisplayName("9 - Camada de serviço: método getAllWorkouts implementado corretamente")
  void testGetAllWorkouts() {
    // Checks if returned workouts have correct values
    List<Workout> allWorkouts = workoutsLoader.fromClasspath("data/test_workouts.json");
    Mockito.when(fakeDatabase.getAllWorkouts()).thenReturn(allWorkouts);

    List<WorkoutDto> returnedWorkouts = workoutsService.getAllWorkouts();

    // Check if fake database was actually called
    Mockito.verify(fakeDatabase).getAllWorkouts();

    // Check if returned values match expected
    assertEquals(
        allWorkouts.stream().map(
            TestHelpers::modelToDto
        ).collect(Collectors.toSet()),
        new HashSet<>(returnedWorkouts)
    );
  }
}
