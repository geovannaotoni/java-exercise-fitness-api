package com.betrybe.fitness.controller;

import com.betrybe.fitness.service.FitnessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fitness")
public class FitnessController implements FitnessControllerInterface {
  private FitnessService service;

  @Autowired
  public FitnessController(FitnessService service) {
    this.service = service;
  }
}
