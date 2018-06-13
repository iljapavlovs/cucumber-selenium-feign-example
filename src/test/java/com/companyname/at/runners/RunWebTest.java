package com.companyname.at.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber/web", "json:target/cucumber/cucumber-web.json", "junit:target/cucumber/cucumberjunit-web.xml"},
        features = {"src/test/resources/features/web"},
        tags = {"@web", "~@disable", "~@demo"},
        glue = {"com.companyname.at.stepdefs", "com.companyname.at.hooks"}
)
public class RunWebTest {

}