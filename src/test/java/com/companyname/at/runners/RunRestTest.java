package com.companyname.at.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber/rest", "json:target/cucumber/cucumber-rest.json", "junit:target/cucumber/cucumberjunit-rest.xml"},
        features = {"src/test/resources/features/restApi"},
        tags = {"@rest", "~@disable", "~@demo"},
        glue = {"com.companyname.at.stepdefs", "com.companyname.at.hooks"}
)
public class RunRestTest {

}