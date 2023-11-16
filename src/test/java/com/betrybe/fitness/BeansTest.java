package com.betrybe.fitness;

import static org.junit.jupiter.api.Assertions.fail;

import com.betrybe.fitness.controller.FitnessControllerInterface;
import com.betrybe.fitness.service.FitnessServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class BeansTest {
  @Autowired
  ApplicationContext context;

  @Test
  @DisplayName("2 - Classe para camada de serviço criada corretamente")
  void testServiceClassExists() {
    try {
      context.getBean(FitnessServiceInterface.class);
    } catch (NoSuchBeanDefinitionException exc) {
      fail("Classe para camada de serviço deve existir e implementar a interface correta");
    }
  }

  @Test
  @DisplayName("3 - Classe para camada de controle criada corretamente")
  void testControllerClassExists() {
    try {
      context.getBean(FitnessControllerInterface.class);
    } catch (NoSuchBeanDefinitionException exc) {
      fail("Classe para camada de controle deve existir e implementar a interface correta");
    }
  }
}
