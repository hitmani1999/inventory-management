package org.eshopping;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.eshopping.config.ConfigIT;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@ContextConfiguration(classes = {ConfigIT.class})
@CucumberOptions(

        plugin = {
                "html:build/cucumber-html-report",
                "json:build/cucumber.json",
                "junit:build/cucumber.xml"
        },
        tags = "not @Ignore",
        features = "src/testInteg/java/org/eshopping/features",
        glue = {"org.eshopping.steps", "org.eshopping.config"}
)
public class TestRunnerIT {
}
