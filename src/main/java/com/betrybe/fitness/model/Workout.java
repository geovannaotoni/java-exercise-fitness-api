package com.betrybe.fitness.model;

import java.util.Objects;

/**
 * Model class.
 */
public class Workout {
  Long id;
  String name;
  Integer repetitions;
  String secretTechnique;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRepetitions() {
    return repetitions;
  }

  public void setRepetitions(Integer repetitions) {
    this.repetitions = repetitions;
  }

  public String getSecretTechnique() {
    return secretTechnique;
  }

  public void setSecretTechnique(String secretTechnique) {
    this.secretTechnique = secretTechnique;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Workout workout = (Workout) o;
    return Objects.equals(id, workout.id) && Objects.equals(name, workout.name)
        && Objects.equals(repetitions, workout.repetitions) && Objects.equals(
        secretTechnique, workout.secretTechnique);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, repetitions, secretTechnique);
  }

}
