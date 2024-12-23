package org.eshopping.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest
@ContextConfiguration(classes = {ConfigIT.class})
public class CucumberSpringConfiguration {

}
