package com.betrybe.fitness.controller;

import com.betrybe.fitness.service.FitnessService;
import com.betrybe.fitness.service.FitnessServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
