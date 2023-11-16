package com.betrybe.fitness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DtoTest {
  @Test
  @DisplayName("1 - Classes de DTO criadas corretamente")
  void testDtoClasses() {
    Map<String, Map<String, String>> expectedMap = Map.of(
        "com.betrybe.fitness.dto.WorkoutDto", Map.of(
            "id", Long.class.getName(),
            "name", String.class.getName(),
            "repetitions", Integer.class.getName()
        ),
        "com.betrybe.fitness.dto.WorkoutCreationDto", Map.of(
            "name", String.class.getName(),
            "repetitions", Integer.class.getName(),
            "secretTechnique", String.class.getName()
        )
    );

    try {
      for (String className : expectedMap.keySet()) {
        Class<?> classToTest = Class.forName(className);
        assertTrue(classToTest.isRecord(), "Os DTOs precisam estar definidos como 'record'");
        assertEquals(
            expectedMap.get(className),
            Arrays.stream(classToTest.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, (f) -> f.getType().getName())),
            "Os atributos precisam estar definidos e com os tipos corretos para a classe"
                + className
        );
      }
    } catch (ClassNotFoundException e) {
      fail("As classes de DTO precisam existir: " + e.getMessage());
    }
  }
}
