package com.example;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
 
@RunWith(Cucumber.class)
@CucumberOptions(glue = "com.example", features = "src\\test\\resources\\features\\Login.feature", plugin = { "pretty",
        "html:target/site/cucumber-pretty",
        "json:target/cucumber.json",
        "usage:target/cucumber-usage.json", "junit:target/cucumber-results.xml" })
public class RunnerIt
{

}
