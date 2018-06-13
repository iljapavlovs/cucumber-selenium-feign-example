package com.companyname.at.hooks;

import com.companyname.at.support.ui.WebDriverUtils;
import com.companyname.at.config.ApplicationProperties;
import com.companyname.at.webdriver.DriverBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.MDC;

import java.io.File;
import java.util.UUID;

@Slf4j
public class Hooks {

    @Before
    public void setup(Scenario scenario) throws Exception {
        MDC.put("scenarioId", "scenario:" + UUID.randomUUID().toString());
        log.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            if (DriverBase.isWebdriverInitialized()) {
                if (ApplicationProperties.getBoolean(ApplicationProperties.ApplicationProperty.REMOTE_DRIVER) &&
                        ApplicationProperties.getBoolean(ApplicationProperties.ApplicationProperty.SELENIUM_GRID_VIDEO_CAPTURE_ENABLED)) {
                    String videoUrlfromGrid = WebDriverUtils.getVideoUrlFromGrid(DriverBase.getDriver());
                    log.info("video recording @ " + videoUrlfromGrid);
                    scenario.write(videoUrlfromGrid);
                }
                try {
                    byte[] screenshot = ((TakesScreenshot) DriverBase.getDriver()).getScreenshotAs(OutputType.BYTES);
                    scenario.embed(screenshot, "image/png");
                    //write screenshot for local execution
                    FileUtils.writeByteArrayToFile(new File(String.format("target\\screenshots\\%s.png", scenario.getName())), screenshot);
                } catch (WebDriverException wde) {
                    log.error(wde.getMessage());
                } catch (ClassCastException cce) {
                    log.error(cce.getLocalizedMessage());
                }
            }
        }
        DriverBase.closeDriverObjects();
        log.info(String.format("Ending Scenario: \"%s\"", scenario.getName()) + " result: " + (scenario.isFailed() ? "FAILED" : "PASSED"));
    }
}
