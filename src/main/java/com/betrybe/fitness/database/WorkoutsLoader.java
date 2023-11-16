package com.betrybe.fitness.database;

import com.betrybe.fitness.model.Workout;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Helper class that load a list of farms.
 */
@Component
public class WorkoutsLoader {
  /**
   * Returns a list of farms loaded from the path argument.
   */
  @Autowired
  ApplicationContext ctx;

  /**
   * Loads farms from classpath resource.
   */
  public List<Workout> fromClasspath(String path) {
    try {
      InputStream file = ctx.getResource("classpath:" + path).getInputStream();
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(file, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
