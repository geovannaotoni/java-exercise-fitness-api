package com.betrybe.fitness;

import static com.betrybe.fitness.utils.TestHelpers.createMockWorkout;
import static com.betrybe.fitness.utils.TestHelpers.modelToCreationDto;
import static com.betrybe.fitness.utils.TestHelpers.modelToDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.betrybe.fitness.database.WorkoutsLoader;
import com.betrybe.fitness.dto.WorkoutCreationDto;
import com.betrybe.fitness.dto.WorkoutDto;
import com.betrybe.fitness.service.FitnessServiceInterface;
import com.betrybe.fitness.utils.TestHelpers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerLayerTest {
  @Autowired
  WorkoutsLoader workoutsLoader;

  @MockBean
  FitnessServiceInterface service;

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("4 - Camada de controle: GET /fitness implementada corretamente")
  void testGetRoot() throws Exception {
    String responseContent = mockMvc.perform(get("/fitness").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    assertEquals("Boas vindas à API de Fitness!", responseContent);
  }


  @Test
  @DisplayName("6 - Camada de controle: GET /fitness/workouts/id implementada corretamente")
  void testGetWorkout() throws Exception {
    WorkoutDto workout = modelToDto(createMockWorkout(11L));
    Mockito.when(service.getWorkout(workout.id())).thenReturn(Optional.of(workout));

    String responseContent = mockMvc.perform(get("/fitness/workouts/11").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    ObjectMapper mapper = new ObjectMapper();
    WorkoutDto returnedWorkout = mapper.readValue(responseContent, WorkoutDto.class);
    assertEquals(workout, returnedWorkout);

    Mockito.verify(service).getWorkout(workout.id());

    Mockito.when(service.getWorkout(22L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/fitness/workouts/22").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    Mockito.verify(service).getWorkout(22L);
  }

  @Test
  @DisplayName("8 - Camada de controle: POST /fitness/workouts/id implementada corretamente")
  void testWorkoutCreation() throws Exception {
    WorkoutDto workout = modelToDto(createMockWorkout(33L));
    Mockito.when(service.saveWorkout(any())).thenReturn(workout);

    WorkoutCreationDto toSaveWorkout = modelToCreationDto(createMockWorkout(null));

    ObjectMapper mapper = new ObjectMapper();
    String requestJson = mapper.writeValueAsString(toSaveWorkout);

    String responseContent = mockMvc.perform(post("/fitness/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);


    WorkoutDto returnedWorkout = mapper.readValue(responseContent, WorkoutDto.class);
    assertEquals(workout, returnedWorkout);

    Mockito.verify(service).saveWorkout(any());
  }

  @Test
  @DisplayName("10 - Camada de controle: GET /fitness/workouts implementada corretamente")
  void testGetAllWorkouts() throws Exception {
    List<WorkoutDto> allWorkouts = workoutsLoader.fromClasspath("data/test_workouts.json")
        .stream().map(TestHelpers::modelToDto).toList();
    Mockito.when(service.getAllWorkouts()).thenReturn(allWorkouts);

    String responseContent = mockMvc.perform(get("/fitness/workouts").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    ObjectMapper mapper = new ObjectMapper();
    List<WorkoutDto> returnedWorkouts = mapper.readValue(responseContent, new TypeReference<>() {
    });
    assertEquals(
        new HashSet<>(allWorkouts),
        new HashSet<>(returnedWorkouts)
    );

    Mockito.verify(service).getAllWorkouts();
  }
}
